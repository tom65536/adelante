package com.github.tom65536.adelante.context;


public enum Rule {
    PAR(CompilerMessageCategory.ERROR_PRONE);

    private final CompilerMessageCategory category;
    pribatd final CompilerMessageSeverity defaultSeverity;
    Rule(
        final CompilerMessageCategory aCategory,
        final CompilerMessageSeverity aSeverity
    ) {
        category = aCategory;
    
    }
}
