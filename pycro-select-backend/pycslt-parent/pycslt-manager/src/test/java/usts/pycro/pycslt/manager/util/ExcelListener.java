package usts.pycro.pycslt.manager.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-29 18:47
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {

    // 封装excel中的数据
    private List<T> data = new ArrayList<>();

    /**
     * 从excel文件的第二行开始读取，把每行读取的内容封装到data中
     *
     * @param t
     * @param analysisContext
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        System.out.println(analysisContext);
        data.add(t);
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
