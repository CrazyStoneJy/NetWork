package me.crazystone.study.okhttp;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by crazy_stone on 17-10-24.
 */

public class OKHttpParams {


    public static MediaType MEDIA_TYPE_OCTET = MediaType.parse("application/octet-stream");
    public static MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain; charset=utf-8");
    public static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    Map<String, String> headerMap = new HashMap<>();
    Map<String, ParamValue> params = new HashMap<>();
    Map<String, List<FileWrapper>> fileMap = new HashMap<>();

    public void putHeader(String key, String value) {
        headerMap.put(key, value);
    }

    public void putFile(String key, FileWrapper... fileWrapper) {
        fileMap.put(key, Arrays.asList(fileWrapper));
    }

    public void put(String key, int value) {
        put(key, String.valueOf(value));
    }

    public void put(String key, float value) {
        put(key, String.valueOf(value));
    }

    public void put(String key, double value) {
        put(key, String.valueOf(value));
    }

    public void put(String key, long value) {
        put(key, String.valueOf(value));
    }

    public void put(String key, String value) {
        put(key, new ParamValue(value));
    }

    public void put(String key, ParamValue value) {
        params.put(key, value);
    }

    public void put(String key, List<String> list) {
        put(key, new ParamValue(list));
    }

    public void put(String key, String[] array) {
        put(key, new ParamValue(Arrays.asList(array)));
    }

    public Map<String, ParamValue> getParams() {
        return this.params;
    }

    public Map<String, String> getHeaderMap() {
        return this.headerMap;
    }

    public Map<String, List<FileWrapper>> getFileMap() {
        return this.fileMap;
    }

    public boolean isParamEmpty() {
        return (params == null || params.size() == 0);
    }

    public boolean isHeaderEmpty() {
        return headerMap == null || headerMap.size() == 0;
    }

    public boolean isFileParamEmpty() {
        return fileMap == null || fileMap.size() == 0;
    }

    public Headers getHeaders() {
        return Headers.of(headerMap);
    }

    public static class ParamValue {
        private String value;
        private Collection<String> values;

        public ParamValue(String value) {
            this.value = value;
        }

        public ParamValue(Collection<String> values) {
            this.values = values;
        }

        public void toMultiParam(String key, MultipartBody.Builder multiBuilder) {
            if (this.value != null) {
                multiBuilder.addFormDataPart(key, value);
            }
            if (this.values != null) {
                for (String string : values) {
                    multiBuilder.addFormDataPart(formatKey(key), string);
                }
            }
        }

        public void toFormParam(String key, FormBody.Builder formBuilder) {
            if (this.value != null) {
                formBuilder.addEncoded(key, this.value);
            }
            if (this.values != null) {
                for (String string : this.values) {
                    formBuilder.addEncoded(formatKey(key), string);
                }
            }
        }

        public void toGetParam(String key, StringBuilder sb) {
            if (this.value != null) {
                sb.append(key).append("=").append(this.value);
            }
            if (this.values != null) {
                Iterator<String> iterator = this.values.iterator();
                int index = 0;
                while (iterator.hasNext()) {
                    if (index != 0) {
                        sb.append("&");
                    }
                    sb.append(formatKey(key)).append("=").append(iterator.next());
                    index++;
                }
            }
        }

        private String formatKey(String key) {
            return key + "[]";
        }
    }

    public static class FileWrapper {
        private File file;

        public FileWrapper(File file) {
            this.file = file;
        }

        private MediaType getMediaType() {
            String fileLowerName = file.getName().toLowerCase(Locale.US);
            if (fileLowerName.endsWith(".jpg") || fileLowerName.endsWith(".jpeg")) {
                return MediaType.parse("image/jpg");
            } else if (fileLowerName.endsWith(".png")) {
                return MediaType.parse("image/png");
            } else if (fileLowerName.endsWith(".gif")) {
                return MediaType.parse("image/gif");
            } else if (fileLowerName.endsWith(".bmp")) {
                return MediaType.parse("image/bmp");
            } else {
                return OKHttpParams.MEDIA_TYPE_OCTET;
            }
        }

        public void addFile(String key, MultipartBody.Builder builder) {
            builder.addFormDataPart(key, file.getName(), RequestBody.create(getMediaType(), file));
        }

    }

}
