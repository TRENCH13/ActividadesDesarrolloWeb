package com.example.clientesolicitudhttp;

import java.util.HashMap;
import java.util.Map;

public class HttpMensajes {
    private static final Map<Integer, String> mensajeHttp = new HashMap<>();
    static {

        mensajeHttp.put(100, "Continue");
        mensajeHttp.put(101, "Switching Protocols");
        mensajeHttp.put(102, "Processing");
        mensajeHttp.put(103, "Early Hints");
        mensajeHttp.put(200, "OK");
        mensajeHttp.put(201, "Created");
        mensajeHttp.put(202, "Accepted");
        mensajeHttp.put(203, "Non-Authoritative Information");
        mensajeHttp.put(204, "No Content");
        mensajeHttp.put(205, "Reset Content");
        mensajeHttp.put(206, "Partial Content");
        mensajeHttp.put(207, "Multi-Status");
        mensajeHttp.put(208, "Already Reported");
        mensajeHttp.put(226, "IM Used");
        mensajeHttp.put(300, "Multiple Choices");
        mensajeHttp.put(301, "Moved Permanently");
        mensajeHttp.put(302, "Found");
        mensajeHttp.put(303, "See Other");
        mensajeHttp.put(304, "Not Modified");
        mensajeHttp.put(305, "Use Proxy");
        mensajeHttp.put(307, "Temporary Redirect");
        mensajeHttp.put(308, "Permanent Redirect");
        mensajeHttp.put(400, "Bad Request");
        mensajeHttp.put(401, "Unauthorized");
        mensajeHttp.put(402, "Payment Required");
        mensajeHttp.put(403, "Forbidden");
        mensajeHttp.put(404, "Not Found");
        mensajeHttp.put(405, "Method Not Allowed");
        mensajeHttp.put(406, "Not Acceptable");
        mensajeHttp.put(407, "Proxy Authentication Required");
        mensajeHttp.put(408, "Request Timeout");
        mensajeHttp.put(409, "Conflict");
        mensajeHttp.put(410, "Gone");
        mensajeHttp.put(411, "Length Required");
        mensajeHttp.put(412, "Precondition Failed");
        mensajeHttp.put(413, "Payload Too Large");
        mensajeHttp.put(414, "URI Too Long");
        mensajeHttp.put(415, "Unsupported Media Type");
        mensajeHttp.put(416, "Range Not Satisfiable");
        mensajeHttp.put(417, "Expectation Failed");
        mensajeHttp.put(418, "I'm a teapot");
        mensajeHttp.put(421, "Misdirected Request");
        mensajeHttp.put(422, "Unprocessable Entity");
        mensajeHttp.put(423, "Locked");
        mensajeHttp.put(424, "Failed Dependency");
        mensajeHttp.put(425, "Too Early");
        mensajeHttp.put(426, "Upgrade Required");
        mensajeHttp.put(428, "Precondition Required");
        mensajeHttp.put(429, "Too Many Requests");
        mensajeHttp.put(431, "Request Header Fields Too Large");
        mensajeHttp.put(451, "Unavailable For Legal Reasons");
        mensajeHttp.put(500, "Internal Server Error");
        mensajeHttp.put(501, "Not Implemented");
        mensajeHttp.put(502, "Bad Gateway");
        mensajeHttp.put(503, "Service Unavailable");
        mensajeHttp.put(504, "Gateway Timeout");
        mensajeHttp.put(505, "HTTP Version Not Supported");
        mensajeHttp.put(506, "Variant Also Negotiates");
        mensajeHttp.put(507, "Insufficient Storage");
        mensajeHttp.put(508, "Loop Detected");
        mensajeHttp.put(510, "Not Extended");
        mensajeHttp.put(511, "Network Authentication Required");
    }

    public static String getMessage(int statusCode) {
        return mensajeHttp.getOrDefault(statusCode, "Unknown");
    }
}
