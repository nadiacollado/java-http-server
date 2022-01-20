import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldTest {

    @Test
    void main() {
        assertNotNull(new HelloWorld());
    }

    @Test
    void sayHello() {
        HelloWorld dummy = new HelloWorld();
        assertEquals("Hello, Nadia", dummy.sayHello("Nadia"));
    }
}