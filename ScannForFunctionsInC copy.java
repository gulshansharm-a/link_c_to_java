import java.util.ArrayList;
import java.util.List;

public class ScannForFunctionsInC {
    static List<String> methodNameList;
    static List<String> dataTypeOfCMethodList;
    static List<String> finalparametersList;
    static List<String> finalparameters_varsList;
    static List<String> typeCastingList;

    public static void main(String[] args) {
        methodNameList = new ArrayList<>();
        dataTypeOfCMethodList = new ArrayList<>();
        finalparametersList = new ArrayList<>();
        finalparameters_varsList = new ArrayList<>();
        typeCastingList = new ArrayList<>();
        // ScannForFunctionsInC scannForFunctionsInC = new ScannForFunctionsInC();  
        // scannForFunctionsInC.get_values_for_method("      public static   int        myMethod (int a , int c){");
        ScannForFunctionsInC scannForFunctionsInC2 = new ScannForFunctionsInC();
        scannForFunctionsInC2. get_values_for_method("      public static   int        myMethod (int a , int c){");  
        scannForFunctionsInC2. get_values_for_method("      public static   int    myMethod (int a , int b){");  

        GenerateDll gd = new GenerateDll();
        gd.generateCppHeader(methodNameList, dataTypeOfCMethodList, "C" + "MainInC", "MainInC", finalparametersList,
                finalparameters_varsList, typeCastingList);
    }

    void get_values_for_method(String s) {
        List<String> primitiveDataTypeList = new ArrayList<>();
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

        List<String> allS = new ArrayList<>();

        String methodName = "";
        String dataTypeOfCMethod = "";
        boolean containsDataType = false;
        List<String> parameterDataTypeWithVarName = new ArrayList<>();
        List<String> parameterDataType = new ArrayList<>();
        List<String> parameterVarName = new ArrayList<>();
        String finalparameters = " ";
        String finalparameters_vars = " ";
        String typeCasting = "";
        parameterDataTypeWithVarName.clear();
        parameterDataType.clear();
        parameterVarName.clear();
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
                        if (false) {
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

        int dataTypeCounter = 0;
        for (String para : parameterDataType) {
            String parName = parameterVarName.get(dataTypeCounter);
            if (parName.contains(")")) {
                parName = parName.split("\\)")[0];
            }

            if (dataTypeCounter == 0) {
                finalparameters += " " + para + " " + parName;
                typeCasting = para + " c_" + parName + " = static_cast<" + para + ">(" + parName + ");";
                finalparameters_vars += parameterVarName.get(dataTypeCounter);
            } else {
                String varname = parameterVarName.get(dataTypeCounter);
                if (varname.contains(")")) {
                    varname = varname.split("\\)")[0];
                }
                finalparameters = finalparameters + " , " + para + " " + parName;
                typeCasting = typeCasting + " \n " + para + " c_" + parName + " = static_cast<" + para + ">(" + parName
                        + ");";
                finalparameters_vars = finalparameters_vars + " , " + varname;
            }
            dataTypeCounter++;
        }

        System.out.println("Method: " + methodName);
        System.out.println("Data Type: " + dataTypeOfCMethod);
        System.out.println("Contains Data Type: " + containsDataType);
        System.out.println("Parameter Data Types: " + parameterDataType);
        System.out.println("Parameter Variable Names: " + parameterVarName);
        System.out.println("Parameter final Names: " + finalparameters);
        System.out.println("Parameter final var: " + finalparameters_vars);

        methodNameList.add(methodName);
        dataTypeOfCMethodList.add(dataTypeOfCMethod);
        finalparametersList.add(finalparameters);
        finalparameters_varsList.add(finalparameters_vars);
        typeCastingList.add(typeCasting);
    }
}
