import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 第一周：必做题1
 */
public class CustomClassLoader extends ClassLoader{
    private String path;

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomClassLoader classLoader = new CustomClassLoader();
        classLoader.path = "Hello.xlass";
        Class<?> clazz = classLoader.loadClass("Hello");
        Method[] methods = clazz.getMethods();
        Object obj = clazz.newInstance();
        for (Method method : methods) {
            if (method.getName().equals("hello")) {
                method.invoke(obj);
            }
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = null;
        byte[] classData = getData();

        if (classData != null) {
            clazz = defineClass(name, classData, 0, classData.length);
        }
        return clazz;
    }


    private byte[] getData() {

        File file = new File(path);
        if (file.exists()){
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1) {
                    for (int i = 0; i < size; i++) {
                        buffer[i] = (byte) (255 - buffer[i]);
                    }
                    out.write(buffer, 0, size);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            return out.toByteArray();
        }else{
            return null;
        }
    }
}
