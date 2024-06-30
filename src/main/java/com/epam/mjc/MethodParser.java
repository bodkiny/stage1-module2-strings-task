package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String[] splitSignature = signatureString.split("[()]");
        String[] signatureWithoutArguments = splitSignature[0].split(" ");

        MethodSignature methodSignature;

        if (signatureHasArguments(splitSignature)) {
            List<MethodSignature.Argument> argumentsList = getArgumentsList(splitSignature);

            if (signatureHasAccessModifier(signatureWithoutArguments)) {
                methodSignature = getMethodSignatureWithAccessModifier(signatureWithoutArguments, argumentsList);
            } else {
                methodSignature = getMethodSignatureWithoutAccessModifier(signatureWithoutArguments, argumentsList);
            }
        } else {
            methodSignature = getMethodSignatureWithoutArguments(signatureWithoutArguments);
        }


        return methodSignature;
    }

    private static MethodSignature getMethodSignatureWithoutArguments(String[] signatureWithoutArguments) {
        MethodSignature methodSignature;
        String methodName = signatureWithoutArguments[2];
        methodSignature = new MethodSignature(methodName);
        methodSignature.setAccessModifier(signatureWithoutArguments[0]);
        methodSignature.setReturnType(signatureWithoutArguments[1]);
        return methodSignature;
    }

    private static MethodSignature getMethodSignatureWithoutAccessModifier(String[] signatureWithoutArguments, List<MethodSignature.Argument> argumentsList) {
        MethodSignature methodSignature;
        String methodName = signatureWithoutArguments[1];
        methodSignature = new MethodSignature(methodName, argumentsList);
        methodSignature.setReturnType(signatureWithoutArguments[0]);
        return methodSignature;
    }

    private static MethodSignature getMethodSignatureWithAccessModifier(String[] signatureWithoutArguments, List<MethodSignature.Argument> argumentsList) {
        MethodSignature methodSignature;
        String methodName = signatureWithoutArguments[2];
        methodSignature = new MethodSignature(methodName, argumentsList);
        methodSignature.setAccessModifier(signatureWithoutArguments[0]);
        methodSignature.setReturnType(signatureWithoutArguments[1]);
        return methodSignature;
    }

    private static boolean signatureHasAccessModifier(String[] signatureWithoutArguments) {
        return signatureWithoutArguments.length > 2;
    }

    private List<MethodSignature.Argument> getArgumentsList(String[] splitSignature) {
        String[] arguments = splitSignature[1].split(", ");
        List<MethodSignature.Argument> argumentsList = new ArrayList<>(arguments.length);
        for (String argument : arguments) {
            String[] s = argument.split(" ");
            argumentsList.add(new MethodSignature.Argument(s[0], s[1]));
        }

        return argumentsList;
    }

    private static boolean signatureHasArguments(String[] splitSignature) {
        return splitSignature.length > 1;
    }
}
