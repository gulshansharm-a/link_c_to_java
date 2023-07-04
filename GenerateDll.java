import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GenerateDll {

    public static void Compilecode(String filename) {
        try {
            Process process = Runtime.getRuntime().exec(
                    "g++ -shared -o CMainInC.dll -I\"C:\\Program Files\\Java\\jdk1.7.0_80\\include\" -I\"C:\\Program Files\\Java\\jdk1.7.0_80\\include\\win32\" -m64 CMainInC.cpp MainInC.cpp");
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

    public static void generateCppHeader(List<String> methodnameList, List<String> returntypeOfMethodList,
            String fileName, String MfileName, List<String> parWithVArList, List<String> onlyVArList,
            List<String> castedStringList) {

        try {
            File headerFile = new File(fileName + ".cpp");
            FileWriter writer = new FileWriter(headerFile);

            writer.write("#include <iostream>\r\n" + "#include <jni.h>\n");
            int a = 0;
            ;
            for (String method : methodnameList) {
                String methodname = method;
                String returntypeOfMethod = returntypeOfMethodList.get(a);
                String parWithVAr = parWithVArList.get(a);
                String castedString = castedStringList.get(a);
                String onlyVAr = onlyVArList.get(a);
                writer.write("extern " + returntypeOfMethod + " " + methodname + "(" + parWithVAr + ");\n");
                String parnmaeOfJIN = parWithVAr
                        .replace("float", "jfloat")
                        .replace("int", "jint").replace("char", "jchar").replace("short", "jshort")
                        .replace("double", "jdouble")
                        .replace("long", "jlong");
                writer.write("extern \"C\" {\n" +
                        "  JNIEXPORT int JNICALL Java_" + fileName + "_" + methodname + "(JNIEnv* env, jobject obj,"
                        + parnmaeOfJIN + ") {\n");
                writer.write(" \n");
                writer.write(castedString + "\n");
                writer.write("     return " + methodname + "(" + onlyVAr + ");\n" + //
                        "  }\n");

                writer.write(" }\n");
                a++;

            }

            writer.close();

            File javaWraper = new File(fileName + ".java");
            FileWriter javaWraperwriter = new FileWriter(javaWraper);
            String defineFunctiooString = "";
            String javaTemplate = "public class " + fileName + " {\n" +
                    "\n" +
                    "    // Load the DLL\n" +
                    "    static {\n" +
                    "        System.loadLibrary(\"" + fileName + "\");\n" +
                    "    }\n" +
                    "\n";
            a = 0;
            ;
            for (String method : methodnameList) 
            {
                String methodname = method;
                String parWithVAr = parWithVArList.get(a);
                String onlyVAr = onlyVArList.get(a);
                defineFunctiooString += " \n   public static native int " + methodname + "(" + parWithVAr + ");\n" +

                        "\n" +
                        "    public int c_" + methodname + "(" + parWithVAr + ") {\n" +
                        "        return " + methodname + "(" + onlyVAr + ");\n" +
                        "    }\n";
            a++;
            }

            javaWraperwriter.write(javaTemplate);
            javaWraperwriter.write(defineFunctiooString);
            javaWraperwriter.write("\n}\n");
            javaWraperwriter.close();
            Compilecode(MfileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}