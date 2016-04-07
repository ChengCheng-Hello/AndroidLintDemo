package com.example;


import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import java.util.Arrays;
import java.util.List;

/**
 * 提供需要被检测的Issue列表
 * <p>
 * Created by Cheng on 16/4/6.
 */
public class TXIssueRegistry extends IssueRegistry {

    @Override
    public List<Issue> getIssues() {
        System.out.println("==== TXIssueRegistry ====");
        return Arrays.asList(
                TXLogDetector.ISSUE
//                DuplicatedActivityIntentFilterDetector.ISSUE,
//                IntentExtraKeyDetector.ISSUE,
//                FragmentArgumentsKeyDetector.ISSUE,
//                LogDetector.ISSUE,
//                PrivateModeDetector.ISSUE,
//                WebViewSafeDetector.ON_RECEIVED_SSL_ERROR,
//                WebViewSafeDetector.SET_SAVE_PASSWORD,
//                WebViewSafeDetector.SET_ALLOW_FILE_ACCESS,
//                WebViewSafeDetector.WEB_VIEW_USE,
//                HashMapForJDK7Detector.ISSUE
        );
    }
}
