import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateDll {
public static void main (String arg[]) {
    generateCppHeader("myMethod","int","C"+"MainInC","MainInC");
}

  public static void Compilecode(String filename) {
try {
            Process process = Runtime.getRuntime().exec("g++ -shared -o CMainInC.dll -I\"C:\\Program Files\\Java\\jdk1.7.0_80\\include\" -I\"C:\\Program Files\\Java\\jdk1.7.0_80\\include\\win32\" -m64 CMainInC.cpp MainInC.cpp" );
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("C++ compilation successful. DLL file created: " + filename);
            } else {
                System.err.println("C++ compilation failed. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        }

    public static void generateCppHeader( String methodname,String returntypeOfMethod ,String fileName ,String MfileName) {
      

        try {
            File headerFile = new File(fileName+".cpp");
            FileWriter writer = new FileWriter(headerFile);

            // Write the header file content
            writer.write("#include <iostream>\r\n" +"#include <jni.h>\n");

            writer.write("extern "+returntypeOfMethod+" "+methodname+"();\n");

            writer.write("extern \"C\" {\n" + 
                    "  JNIEXPORT int JNICALL Java_"+fileName+"_"+methodname+"(JNIEnv* env, jobject obj) {\n");

            writer.write("     return "+methodname+"();\n" + //
                    "  }\n"); 

            writer.write(" }\n" );

            writer.close();

            File javaWraper = new File(fileName+".java");
            FileWriter javaWraperwriter = new FileWriter(javaWraper);

            String javaTemplate = 
                "public class "+fileName+" {\n" +
                "\n" +
                "    // Load the DLL\n" +
                "    static {\n" +
                "        System.loadLibrary(\""+fileName+"\");\n" +
                "    }\n" +
                "\n" +
                "    public static native int "+methodname+"();\n" +
                "\n" +
                "    public int c_"+methodname+"() {\n" +
                "        return "+methodname+"();\n" +
                "    }\n" +
                "}\n";

            javaWraperwriter.write(javaTemplate);
            javaWraperwriter.close();
            Compilecode(MfileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
      
    }
    
}