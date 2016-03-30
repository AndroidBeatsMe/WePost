//package com.nancyberry.wepost.common.network;
//
//import android.util.Pair;
//
//import com.hulu.physicalplayer.utils.IOUtils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * Created by yicheng.liu on 1/29/16.
// */
//public class MultipartByteArrayHttpServiceCall extends HttpServiceCall<List<byte[]>> {
//
//    private static final String TAG = MultipartByteArrayHttpServiceCall.class.getSimpleName();
//
//    /**
//     * Constructor for HttpServiceCall class
//     *
//     * @param request  PlusRequest which contains metadata for the HTTP request
//     * @param executor The executor used for executing HTTP request task
//     */
//    public MultipartByteArrayHttpServiceCall(PlusRequest request, IExecutor executor) {
//        super(request, executor);
//    }
//
//    public MultipartByteArrayHttpServiceCall(PlusRequest request) {
//        super(request, null);
//    }
//
//    @Override
//    protected void callSuccessHandlers(IServiceResponse response) {
//        try {
//            InputStream result = response.getStream();
//            String rangeParam = plusRequest.getHeaderParams().get("Range");
//            List<byte[]> ret;
//
//            if (rangeParam == null) {
//                ret = Collections.singletonList(IOUtils.toByteArray(result));
//            } else {
//                List<Pair<Integer, Integer>> ranges = new ArrayList<>();
//                for (String byteRange : rangeParam.split("bytes=")[1].split(",\\s*")) {
//                    String[] startEnd = byteRange.split("-");
//
//                    ranges.add(Pair.create(Integer.valueOf(startEnd[0]), Integer.valueOf(startEnd[1])));
//                }
//
//                if (ranges.size() == 1) {
//                    int size = ranges.get(0).second - ranges.get(0).first + 1;
//                    byte[] buffer = new byte[size];
//
//                    readFully(result, buffer, size);
//
//                    ret = Collections.singletonList(buffer);
//                } else {
//                    String contentType = response.getHeader("Content-Type", "");
//
//                    if (!contentType.contains("multipart")) {
//                        callFailureHandlers(new UnsupportedOperationException("Multipart not supported by server"));
//                        return;
//                    }
//
//                    ret = new ArrayList<>(ranges.size());
//
//                    for (Pair<Integer, Integer> range : ranges) {
//                        readUntilLineBreak(result); //line break, empty
//                        while (readUntilLineBreak(result) != 0) {
//                            //read boundary and headers until empty line
//                        }
//
//                        int size = range.second - range.first + 1;
//                        byte[] buffer = new byte[size];
//
//                        readFully(result, buffer, size);
//
//                        ret.add(buffer);
//                    }
//                }
//            }
//
//            if (successHandlers != null) {
//                for (ServiceSuccessHandler<List<byte[]>> successHandler : successHandlers) {
//                    successHandler.onSuccess(ret);
//                }
//            }
//        } catch (IOException e) {
//            callFailureHandlers(e);
//        } finally {
//            consumeResponse();
//        }
//    }
//
//    private void readFully(InputStream in, byte[] buffer, int size) throws IOException {
//        int read = 0;
//        while (read < size) {
//            int len = in.read(buffer, read, size - read);
//
//            if (len == -1) {
//                throw new IOException();
//            }
//
//            read += len;
//        }
//    }
//
//    private int readUntilLineBreak(InputStream in) throws IOException {
//        int readCount = 0;
//
//        int state = 0; //0 for normal, 1 when meets '\r'
//        int c;
//
//        while ((c = in.read()) != -1) {
//            byte b = (byte) (c & 0xff);
//
//            if (state == 0) {
//                readCount++;
//
//                if (b == 0x0D) {
//                    state = 1;
//                }
//            } else {
//                if (b == 0x0A) {
//                    return readCount - 1;
//                } else {
//                    readCount++;
//
//                    if (b == 0x0D) {
//                        state = 1;
//                    } else {
//                        state = 0;
//                    }
//                }
//            }
//        }
//
//        throw new IOException();
//    }
//}
