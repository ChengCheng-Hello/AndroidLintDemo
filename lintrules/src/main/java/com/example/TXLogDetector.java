package com.example;

import com.android.ddmlib.Log;
import com.android.tools.lint.client.api.JavaParser;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.Collections;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.MethodInvocation;
import lombok.ast.Node;

/**
 * TXLogDetector继承Detector,实现Scanner接口
 * <p>
 * 自定义Detector可以实现一个或多个Scanner接口，选择实现哪种接口取决于你想要的扫描范围
 * - Detector.XmlScanner
 * - Detector.GradleScanner
 * - ...
 * <p>
 * Created by Cheng on 16/4/6.
 */
public class TXLogDetector extends Detector implements Detector.JavaScanner {

    private static final String TAG = "TXLogDetector";

    public static final Issue ISSUE = Issue.create("LogUse",// 唯一值，能简短表明问题
            "避免使用Log/System.out.println",// 简短的总结
            "使用TXLog，防止在正式包打印log",// 完整的问题解释和修复建议
            Category.SECURITY,// 问题类别，可自定义
            5,// 优先级。10为最高
            Severity.ERROR,// 严重级别
            new Implementation(TXLogDetector.class, Scope.JAVA_FILE_SCOPE)// 为Issue和Detector提供映射关系
    );

    /**
     * 决定什么样的类型能够检测到。
     *
     * @return
     */
    @Override
    public List<Class<? extends Node>> getApplicableNodeTypes() {
        return Collections.<Class<? extends Node>>singletonList(MethodInvocation.class);
    }

    @Override
    public AstVisitor createJavaVisitor(final JavaContext context) {
        return new ForwardingAstVisitor() {

            /**
             * 检测方法调用 MethodInvocation
             *
             * @param node
             * @return
             */
            @Override
            public boolean visitMethodInvocation(MethodInvocation node) {

                if (node.toString().startsWith("System.out.println")) {
                    context.report(ISSUE, node, context.getLocation(node), "请使用TXLog");
                    return true;
                }

                JavaParser.ResolvedNode resolvedNode = context.resolve(node);
                if (resolvedNode instanceof JavaParser.ResolvedMethod) {
                    JavaParser.ResolvedMethod method = (JavaParser.ResolvedMethod) resolvedNode;
                    JavaParser.ResolvedClass containingClass = method.getContainingClass();
                    if (containingClass.matches("android.util.Log")) {
                        context.report(ISSUE, node, context.getLocation(node), "请使用TXLog");
                        return true;
                    }
                }

                return super.visitMethodInvocation(node);
            }
        };
    }

    /**
     * 指定方法名称
     *
     * @return
     */
    @Override
    public List<String> getApplicableMethodNames() {
        Log.d(TAG, "getApplicableMethodNames");
        return super.getApplicableMethodNames();
    }

    /**
     * 接受检测到的方法
     *
     * @param context
     * @param visitor
     * @param node
     */
    @Override
    public void visitMethod(JavaContext context, AstVisitor visitor, MethodInvocation node) {
        Log.d(TAG, "visitMethod node: " + node.toString());
        super.visitMethod(context, visitor, node);
    }
}
