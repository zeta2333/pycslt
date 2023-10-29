package usts.pycro.pycslt.manager.util;

import com.alibaba.excel.EasyExcel;
import usts.pycro.pycslt.model.vo.product.CategoryExcelVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-29 18:43
 */
public class EasyExcelTest {
    public static void main(String[] args) {
        // 读操作
        read();
        // 写操作
        // write();
    }

    public static void read() {
        // 1 定义读取文件的名称
        String fileName = "C:\\Users\\20237\\Desktop\\测试数据.xlsx";
        // 2 调用方法
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>();
        EasyExcel.read(fileName, CategoryExcelVo.class, excelListener)
                .sheet().doRead();
        List<CategoryExcelVo> data = excelListener.getData();
        data.forEach(System.out::println);
    }

    public static void write() {
        List<CategoryExcelVo> list = new ArrayList<>();
        list.add(new CategoryExcelVo(1L, "数码办公", "", 0L, 1, 1));
        list.add(new CategoryExcelVo(11L, "华为手机", "", 1L, 1, 2));
        EasyExcel.write("C:\\Users\\20237\\Desktop\\测试数据2.xlsx", CategoryExcelVo.class)
                .sheet("分类数据").doWrite(list);
    }
}
