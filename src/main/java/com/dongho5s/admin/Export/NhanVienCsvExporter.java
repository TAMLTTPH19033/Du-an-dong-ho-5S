package com.dongho5s.admin.Export;

import com.dongho5s.admin.Entity.NhanVien;
import com.dongho5s.admin.Service.Impl.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NhanVienCsvExporter extends AbstractExporter {

    public void export(List<NhanVien> listNhanVien, HttpServletResponse response)throws IOException {
        super.setResponseHeader(response,"text/csv",".csv");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"User ID", "Adress","E-mail","LastName","FirstName","Phone","Roles","Enabled"};
        String[] filedMapping = {"id","diaChi","email","ho","ten","soDienThoai","chucVu","enabled"};

        csvBeanWriter.writeHeader(csvHeader);
        for (NhanVien nhanVien: listNhanVien){
            csvBeanWriter.write(nhanVien,filedMapping);
        }
        csvBeanWriter.close();
    }
}
