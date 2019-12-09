package com.tianwen.springcloud.scoreapi.service.util.cache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by kimchh on 12/10/2018.
 */
@Service
public class AnalysisDataCacheService {

    private static final String DEFAULT_STORAGE_PATH =  "cache/analysis/";

    @Value("${analysis-data.cache.enabled}")
    protected Boolean enabled;

    @Value("${analysis-data.cache.storage.path}")
    protected String storagePath;

    @Autowired
    protected ServletContext servletContext;

    public String getStoragePath() {
        String path = storagePath;

        if (StringUtils.isEmpty(storagePath)) {
            path = servletContext.getRealPath("/") + DEFAULT_STORAGE_PATH;
        }

        if (!path.endsWith(File.pathSeparator)) {
            path += File.separator;
        }

        return path;
    }
    public <T> List<T> loadListData(Class<T> clazz, String examId, String moduleKey, String dataKey) {
        if (!isCacheEnabled()) {
            return null;
        }

        String data = load(examId, moduleKey, dataKey);

        if (data != null) {
            return JSONArray.parseArray(data, clazz);
        }

        return null;
    }

    public <T> List<T> saveListData(String examId, String moduleKey, String dataKey, List<T> listData) {
        if (!isCacheEnabled()) {
            return listData;
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(listData.stream().map(JSONObject::toJSON).collect(Collectors.toList()));

        try {
            save(examId, moduleKey, dataKey, jsonArray.toJSONString());
        } catch (IOException e) {

        }

        return listData;
    }


    public <T> T loadData(String moduleKey, String dataKey) {
        if (!isCacheEnabled()) {
            return null;
        }

        return null;
    }

    public <T> T saveData(String moduleKey, String dataKey, T data) {
        if (!isCacheEnabled()) {
            return data;
        }

        return null;
    }

    public boolean isCacheEnabled() {
        return enabled != null && enabled;
    }

    protected String load(String examId, String moduleKey, String dataKey) {
        String data = null;

        String path = getStoragePath() + examId + File.separator + moduleKey + File.separator;

        File file = new File(path + dataKey + ".json");
        if (!file.exists()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            StringBuilder buffer = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                if (buffer.length() > 0) {
                    buffer.append("\n");
                }
                buffer.append(line);
            }

            data = buffer.toString();
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }

        return data;
    }

    synchronized protected void save(String examId, String moduleKey, String dataKey, String data) throws IOException {

        String path = getStoragePath() + examId + File.separator + moduleKey + File.separator;
        if (!new File(path).mkdirs()) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, String.format("Failed to create cache directory for analysis data. (%s)", path));
        }

        File file = new File(path + dataKey + ".json");

        BufferedWriter writer = null;
        FileLock lock = null;
        try (final FileOutputStream stream = new FileOutputStream(file);
             final FileChannel chan = stream.getChannel()) {
            lock = chan.lock();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            writer.write(data);
        } catch (IOException e) {
            if (file.exists()) {
                if (file.delete()) {
                    //...
                }
            }
        } finally {
            if (lock != null) {
                lock.release();
            }
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public void clear(List<String> examIdList) {
        examIdList.forEach(this::clear);
    }
    public void clear(String examId) {
        String path = getStoragePath() + examId;
        File file = new File(path);
        if (file.exists()) {
            deleteDir(file);
        }
    }

    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (null != children) {
                for (String aChildren : children) {
                    boolean success = deleteDir(new File(dir, aChildren));
                    if (!success) {
                        return false;
                    }
                }
                return dir.delete();
            }
        }
        return false;
    }
}
