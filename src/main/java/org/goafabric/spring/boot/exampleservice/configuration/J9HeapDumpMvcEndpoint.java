package org.goafabric.spring.boot.exampleservice.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.management.HeapDumpWebEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

//Class that makes HeapDump Endpoint work with OpenJ9, original code is from: https://github.com/sa1nt/ibm-heapdump-spring-boot-actuator
@Component
public class J9HeapDumpMvcEndpoint extends HeapDumpWebEndpoint {
    public J9HeapDumpMvcEndpoint() {
        super(TimeUnit.SECONDS.toMillis(10));
    }

    @Override
    protected HeapDumper createHeapDumper() throws HeapDumperUnavailableException {
        try {
            final Class<?> ibmDumpClass = ClassUtils.forName("com.ibm.jvm.Dump", null);
            return new J9JvmHeapDumper(ReflectionUtils.findMethod(ibmDumpClass, "heapDumpToFile", String.class));
        } catch (ClassNotFoundException e) { //if openj9 is not available create the normal HeapDumper
            return super.createHeapDumper();
        }
    }

    private static class J9JvmHeapDumper implements HeapDumper {
        private final Method dumpMethod;

        J9JvmHeapDumper(Method dumpMethod) {
            this.dumpMethod = dumpMethod;
        }

        public void dumpHeap(File file, boolean live) {
            ReflectionUtils.invokeMethod(dumpMethod, null, file.getAbsolutePath());
        }
    }
}