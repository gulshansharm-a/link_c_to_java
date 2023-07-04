import java.util.ArrayList;
import java.util.List;

public class ScannForFunctionsInC {
    static List<String> primitiveDataTypeList;

    public static void main(String[] args) {
        String s = "      public static   int        main ( int ) {";
        List<String> allS = new ArrayList<>();
        assignPrimitives();
        String methodName = "";
        String dataTypeOfCMethod = "";
        boolean containsDataType = false;
        List<String> parameterDataTypeWithVarName = new ArrayList<>();
        List<String> parameterDataType = new ArrayList<>();
        List<String> parameterVarName = new ArrayList<>();
        
        for (String single : s.split(" ")) {
            if (!single.trim().equals("")) {
                allS.add(single.trim());
            }
        }

        for (String single : allS) {
            System.out.println("string " + single);
        }

        for (String dataType : primitiveDataTypeList) {
            if (s.contains(dataType)) {
                dataTypeOfCMethod = dataType;
                int i = 0;
                for (String newString : allS) {
                    if (newString.equals(dataType)) {
                        methodName = allS.get(i + 1);
                        if (allS.get(i + 3).contains(")")) {
                            containsDataType = false;
                        } else {
                            containsDataType = true;
                            // Getting parameters
                            String s2 = s.split("\\(")[1];
                            System.out.println(s2);
                            // Running loop after '(' to get data types and variable names
                            for (String parameter : s2.split(" ")) {
                                if (!parameter.trim().equals("")) {
                                    parameterDataTypeWithVarName.add(parameter.trim());
                                }
                            }
                            int runner = 0;
                            for (String dataTypeAndVar : parameterDataTypeWithVarName) {
                                if (primitiveDataTypeList.contains(dataTypeAndVar)) {
                                    parameterDataType.add(dataTypeAndVar);
                                    parameterVarName.add(parameterDataTypeWithVarName.get(runner + 1));
                                }
                                runner++;
                            }
                            break;
                        }
                    }
                    i++;
                }
            }
        }

        System.out.println("Method: " + methodName);
        System.out.println("Data Type: " + dataTypeOfCMethod);
        System.out.println("Contains Data Type: " + containsDataType);
        System.out.println("Parameter Data Types: " + parameterDataType);
        System.out.println("Parameter Variable Names: " + parameterVarName);

    }

    static void assignPrimitives() {
        primitiveDataTypeList = new ArrayList<String>();
        primitiveDataTypeList.add("int");
        primitiveDataTypeList.add("short");
        primitiveDataTypeList.add("long long");
        primitiveDataTypeList.add("float");
        primitiveDataTypeList.add("double");
        primitiveDataTypeList.add("long double");
        primitiveDataTypeList.add("char");
        primitiveDataTypeList.add("wchar_t");
        primitiveDataTypeList.add("void");
        primitiveDataTypeList.add("bool");
    }
    
}
