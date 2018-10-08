package com.company;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ScanReader {
    private byte[] buf = new byte[4 * 1024];
    private int index;
    private BufferedInputStream in;
    private int total;

    public ScanReader(InputStream inputStream) {
        in = new BufferedInputStream(inputStream);
    }

    private int scan() throws IOException {
        if (index >= total) {
            index = 0;
            total = in.read(buf);
            if (total <= 0) return -1;
        }
        return buf[index++];
    }
    public char scanChar() throws IOException {
        int c=scan();
        while (isWhiteSpace(c))c=scan();
        return (char)c;
    }


    public int scanInt() throws IOException {
        int integer = 0;
        int n = scan();
        while (isWhiteSpace(n)) n = scan();
        int neg = 1;
        if (n == '-') {
            neg = -1;
            n = scan();
        }
        while (!isWhiteSpace(n)) {
            if (n >= '0' && n <= '9') {
                integer *= 10;
                integer += n - '0';
                n = scan();
            }
        }
        return neg * integer;
    }

    public String scanString() throws IOException {
        int c = scan();
        while (isWhiteSpace(c)) c = scan();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = scan();
        } while (!isWhiteSpace(c));
        return res.toString();
    }

    private boolean isWhiteSpace(int n) {
        if (n == ' ' || n == '\n' || n == '\r' || n == '\t' || n == -1) return true;
        else return false;
    }

    public long scanLong() throws IOException {
        long integer = 0;
        int n = scan();
        while (isWhiteSpace(n)) n = scan();
        int neg = 1;
        if (n == '-') {
            neg = -1;
            n = scan();
        }
        while (!isWhiteSpace(n)) {
            if (n >= '0' && n <= '9') {
                integer *= 10;
                integer += n - '0';
                n = scan();
            }
        }
        return neg * integer;
    }

    public void scanLong(long[] A) throws IOException {
        for (int i = 0; i < A.length; i++) A[i] = scanLong();
    }

    public void scanInt(int[] A) throws IOException {
        for (int i = 0; i < A.length; i++) A[i] = scanInt();
    }

    public double scanDouble() throws IOException {
        int c = scan();
        while (isWhiteSpace(c)) c = scan();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = scan();
        }
        double res = 0;
        while (!isWhiteSpace(c) && c != '.') {
            if (c == 'e' || c == 'E') {
                return res * Math.pow(10, scanInt());
            }
            res *= 10;
            res += c - '0';
            c = scan();
        }
        if (c == '.') {
            c = scan();
            double m = 1;
            while (!isWhiteSpace(c)) {
                if (c == 'e' || c == 'E') {
                    return res * Math.pow(10, scanInt());
                }
                m /= 10;
                res += (c - '0') * m;
                c = scan();
            }
        }
        return res * sgn;
    }

    public float scanFloat() throws IOException {
        int c = scan();
        while (isWhiteSpace(c)) c = scan();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = scan();
        }
        float res = 0;
        while (!isWhiteSpace(c) && c != '.') {
            if (c == 'e' || c == 'E') {
                return (float) (res * Math.pow(10, scanInt()));
            }
            res *= 10;
            res += c - '0';
            c = scan();
        }
        if (c == '.') {
            c = scan();
            float m = 1;
            while (!isWhiteSpace(c)) {
                if (c == 'e' || c == 'E') {
                    return (float) (res * Math.pow(10, scanInt()));
                }
                m /= 10;
                res += (c - '0') * m;
                c = scan();
            }
        }
        return res * sgn;
    }

}



