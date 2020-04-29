package me.kingcjy.ezcommand.command.resolver.support;

import me.kingcjy.ezcommand.annotations.CommandMapping;
import me.kingcjy.ezcommand.annotations.Component;
import me.kingcjy.ezcommand.annotations.PathVariable;
import me.kingcjy.ezcommand.command.CommandArgument;
import me.kingcjy.ezcommand.command.CommandParser;
import me.kingcjy.ezcommand.command.definition.CommandTypeDefinitionComposite;
import me.kingcjy.ezcommand.command.resolver.HandlerMethodArgumentResolver;
import me.kingcjy.ezcommand.executor.method.MethodParameter;

import java.util.HashMap;
import java.util.Map;

@Component
public class PathVariableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private Map<String, CommandParser> commandParserCache = new HashMap<>();

    private final CommandTypeDefinitionComposite commandTypeDefinitionComposite;

    public PathVariableHandlerMethodArgumentResolver(CommandTypeDefinitionComposite commandTypeDefinitionComposite) {
        this.commandTypeDefinitionComposite = commandTypeDefinitionComposite;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(PathVariable.class) != null;
    }

    @Override
    public Object resolveParameter(CommandArgument commandArgument, MethodParameter parameter) {
        PathVariable pathVariable = parameter.getParameterAnnotation(PathVariable.class);
        String command = getMappingCommand(parameter);

        if(commandParserCache.get(command) == null) {
            CommandParser commandParser = new CommandParser(commandTypeDefinitionComposite, command);
            commandParserCache.put(command, commandParser);
        }

        String parameterName = getParameterName(pathVariable, parameter);

        CommandParser commandParser = commandParserCache.get(command);

        return commandParser.trasform(commandArgument, parameterName, parameter.getParameterType());
    }

    private String getMappingCommand(MethodParameter parameter) {
        String classMappingCommand = getClassMappingCommand(parameter);
        String methodMappingCommand = getMethodMappingCommand(parameter);

        if("".equals(classMappingCommand)) {
            return methodMappingCommand;
        }

        return classMappingCommand + " " + methodMappingCommand;
    }

    private String getClassMappingCommand(MethodParameter parameter) {
        CommandMapping commandMapping = parameter.getMethod().getDeclaringClass().getAnnotation(CommandMapping.class);

        if(commandMapping == null) {
            return "";
        }

        return commandMapping.value();
    }

    private String getMethodMappingCommand(MethodParameter parameter) {
        CommandMapping commandMapping = parameter.getMethod().getAnnotation(CommandMapping.class);

        return commandMapping.value();
    }

    private String getParameterName(PathVariable pathVariable, MethodParameter parameter) {
        if("".equals(pathVariable.name()) == false) {
            return pathVariable.name();
        }

        return parameter.getName();
    }
}
