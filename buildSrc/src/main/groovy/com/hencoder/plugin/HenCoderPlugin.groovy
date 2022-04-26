package com.hencoder.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

class HenCoderPlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        def extension = target.extensions.create('hencoder', HenCoderExtension)
        target.afterEvaluate {
            println "Hi ${extension.name}!"
        }

        def transform = new HenCoderTransform()

        //因为要干预 android 的打包过程, 所以需要使用 etByType(BaseExtension)
        //拿到 android {} 这个 Extension
        def baseExtension = target.extensions.getByType(BaseExtension)

        //注册 transform 对象
        baseExtension.registerTransform(transform)
    }
}