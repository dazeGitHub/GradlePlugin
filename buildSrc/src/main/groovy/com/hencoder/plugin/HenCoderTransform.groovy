package com.hencoder.plugin

import com.android.build.api.transform.Transform
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import org.gradle.internal.impldep.org.eclipse.jgit.annotations.NonNull
import sun.instrument.TransformerManager

class HenCoderTransform extends Transform {

    //右侧任务会有 hencoder
    @Override
    String getName() {
        return "hencoder"
    }

    //从 Android 拿到 class 文件后再返回给 Android, 输入是 class 文件 / jar 文件 / 资源文件 等,
    //如果只想要 class 文件 / jar 文件, 那么可以在这个 getInputTypes() 方法内配置
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformerManager.CONTENT_CLASS
    }

    //要对那些项目进行处理, 对主项目、子项目、第三方库, 或者是都要?
    //如果 TransformerManager.SCOPE_FULL_PROJECT 不符合需求, 那么点击 TransformerManager.SCOPE_FULL_PROJECT 可得 :
    //public static final Set<ScopeType> SCOPE_FULL_PROJECT =
    //      ImmutableSet.of(Scope.PROJECT, Scope.SUB_PROJECTS, Scope.EXTERNAL_LIBRARIES)
    //所以将 TransformerManager.SCOPE_FULL_PROJECT 替换为该 ImmutableSet 效果相同
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformerManager.SCOPE_FULL_PROJECT
        //return ImmutableSet.of(QualifiedContent.Scope.PROJECT, QualifiedContent.Scope.SUB_PROJECTS, QualifiedContent.Scope.EXTERNAL_LIBRARIES)
        //只想要主项目 和 外部库
        //return ImmutableSet.of(QualifiedContent.Scope.PROJECT, QualifiedContent.Scope.EXTERNAL_LIBRARIES)
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(@NonNull TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {
        //默认 super.transform(transformInvocation) 是个空方法
        //super.transform(transformInvocation)

        //如果要实现正常的没有 Transform 的效果, 那么可以使用如下代码 :
        //这是一个输入输出的过程
        def inputs = transformInvocation.inputs
        def outputProvider = transformInvocation.outputProvider
        inputs.each {
            it.jarInputs.each {
                File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.JAR)
                println "Jar: ${it.file, Dest: ${dest}}"
                FileUtils.copyFile(it.file, dest)
            }
            it.directoryInputs.each {
                File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
                println "Dir: ${it.file}"
                FileUtils.copyDirectory(it.file, dest)
            }
        }
    }
}
