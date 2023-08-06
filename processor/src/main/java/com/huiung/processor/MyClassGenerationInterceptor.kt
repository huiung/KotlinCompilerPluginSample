package com.huiung.processor

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.ClassBuilderFactory
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
import org.jetbrains.kotlin.diagnostics.DiagnosticSink
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.descriptorUtil.annotationClass
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes
import org.jetbrains.org.objectweb.asm.Opcodes.ARETURN
import org.jetbrains.org.objectweb.asm.Opcodes.DUP
import org.jetbrains.org.objectweb.asm.Opcodes.GETSTATIC
import org.jetbrains.org.objectweb.asm.Opcodes.INVOKESPECIAL
import org.jetbrains.org.objectweb.asm.Opcodes.INVOKEVIRTUAL
import org.jetbrains.org.objectweb.asm.Opcodes.IRETURN
import org.jetbrains.org.objectweb.asm.Opcodes.NEW
import org.jetbrains.org.objectweb.asm.Opcodes.RETURN
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter

//class TiaraLogIRExtension(
//
//) : IrGenerationExtension {
//    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
//        moduleFragment.transform()
//    }
//}
//
//class TiaraLogVisitor(

//) : IrElementVisitorVoid {
//    override fun visitFunction(declaration: IrFunction) {
//        declaration.body
//        super.visitFunction(declaration)
//    }
//}

class MyClassGenerationInterceptor(
//    val myAnnotations: List<String>
    val logger: MessageCollector
) : ClassBuilderInterceptorExtension {
    override fun interceptClassBuilderFactory(
        interceptedFactory: ClassBuilderFactory,
        bindingContext: BindingContext,
        diagnostics: DiagnosticSink
    ): ClassBuilderFactory = object : ClassBuilderFactory by interceptedFactory {
        override fun newClassBuilder(origin: JvmDeclarationOrigin): ClassBuilder {
            return MyClassBuilder(
//                myAnnotations,
                logger,
                interceptedFactory.newClassBuilder(origin)
            )
        }
    }
}

private class MyClassBuilder(
//    val annotations: List<String>,
    val logger: MessageCollector,
    val delegateBuilder: ClassBuilder
) : DelegatingClassBuilder() {

    override fun newMethod(
        origin: JvmDeclarationOrigin,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val original = super.newMethod(origin, access, name, desc, signature, exceptions)

        var hasAnnotation = false

        origin.descriptor?.annotations?.forEach {

            logger.report(CompilerMessageSeverity.WARNING, "내부")
            logger.report(CompilerMessageSeverity.WARNING, "${it.annotationClass}")

            if (it.annotationClass?.name?.asString() == "MyFunction") {
                logger.report(CompilerMessageSeverity.WARNING, "어노테이션 존재")
                hasAnnotation = true
            }
        }

        if (!hasAnnotation) {
            return original
        }

//        if (annotations.none {  }) {
//            return original
//        }

        return object : MethodVisitor(Opcodes.ASM9, original) {
            override fun visitInsn(opcode: Int) {
                when (opcode) {
                    //void, object, int
                    RETURN, ARETURN, IRETURN -> {
                        //method exit
                        InstructionAdapter(this).apply {
                            // System.out
                            visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            visitLdcInsn("이 출력은 컴파일러 플러그인에 의해 생성된 코드다.");
                            visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

                            // Step 1: Create new instance of MyClass
                            visitTypeInsn(NEW, "com/huiung/MyClass")
                            visitInsn(DUP)
                            visitMethodInsn(INVOKESPECIAL, "com/huiung/MyClass", "<init>", "()V", false)

                            // Step 2: Call myFunction
                            visitMethodInsn(INVOKEVIRTUAL, "com/huiung/MyClass", "myFunction", "()V", false)

                            //object's function(static)
//                            visitMethodInsn(INVOKESTATIC, "com/huiung/MyClass", "myFunction", "()V", false)

                        }
                    }
                }
                super.visitInsn(opcode)
            }
        }
    }

    override fun getDelegate(): ClassBuilder = delegateBuilder
}