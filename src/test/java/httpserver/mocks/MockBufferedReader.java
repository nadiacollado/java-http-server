package httpserver.mocks;

import java.io.BufferedReader;
import java.io.Reader;

public class MockBufferedReader extends BufferedReader {
    private boolean readLineWasCalled = false;
    private boolean readWasCalled = false;
    private boolean closeCalled = false;
    private String[] readLineValues;
    private int counterIdx = 0;
    private String stringBody = "";

    public MockBufferedReader(Reader in) {
        super(in);
    }

    public String readLine() {
        readLineWasCalled = true;
        if (counterIdx < readLineValues.length) {
            String line = readLineValues[counterIdx];
            counterIdx++;
            return line;
        }
        return "";
    }

    public int read(char[] container, int offset, int length) {
        readWasCalled = true;
        char[] mockContainer = stringBody.toCharArray();
        for (int i = 0; i < container.length; i++) {
            container[i] = mockContainer[i];
        }
        return -1;
    }

    public void close() {
        closeCalled = true;
    }

    public void setReadLineValues(String[] lineValues) {
        readLineValues = lineValues;
    }

    public void setReadValues(String bodyLine) {
        stringBody = bodyLine;
    }

    public boolean wasReadLineCalled() {
        return readLineWasCalled;
    }

    public boolean wasReadCalled() {
        return readWasCalled;
    }

    public boolean wasCloseCalled() {
        return closeCalled;
    }
}
