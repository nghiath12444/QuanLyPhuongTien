package com.example.mock1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mock1.PhuongTien;
import com.example.mock1.PhuongTienDAO;
import com.example.mock1.R;
import com.example.mock1.adpater.PhuongTienAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DanhSachActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PhuongTienDAO dao;
    List<PhuongTien> list;
    PhuongTienAdapter adapter;
    FloatingActionButton fab;
    TextView ten, gia;
    int lengt_list;
    int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);
        recyclerView = findViewById(R.id.rcv);
        dao = new PhuongTienDAO(this);
        setTitle("Quản lý phương tiện");
        list = new ArrayList<>();
        ten = findViewById(R.id.tv_ten);
        gia = findViewById(R.id.tv_gia);
        list = dao.getList();
        ten.setTextColor(Color.BLACK);
        gia.setTextColor(Color.BLACK);
        sap_xep();

        adapter = new PhuongTienAdapter(list, this);

        recyclerView.setAdapter(adapter);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.mnu_xoa:
                xoa();
                break;
            case R.id.mnu_loc:
                loc();
                break;
            case R.id.mnu_search:
                search();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    void add(){
        Dialog dialog = new Dialog(DanhSachActivity.this);
        dialog.setContentView(R.layout.dialog_them_sua_phuongtien);
        EditText id, ten, loai, gia;
        Button huy, them;
        huy = dialog.findViewById(R.id.btn_huy);
        them = dialog.findViewById(R.id.btn_them_sua);
        id = dialog.findViewById(R.id.edt_id);
        ten = dialog.findViewById(R.id.edt_ten);
        loai = dialog.findViewById(R.id.edt_loai);
        gia = dialog.findViewById(R.id.edt_gia);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id.getText().toString().equalsIgnoreCase("")){
                    id.setError("Bạn chưa nhập ID");
                }
                else if (ten.getText().toString().equalsIgnoreCase("")){
                    ten.setError("Bạn phải nhập tên phương tiện");
                }
                else if (loai.getText().toString().equalsIgnoreCase("")){
                    loai.setError("Bạn phải nhập loại phương tiện");
                }
                else if (gia.getText().toString().equalsIgnoreCase("")){
                    gia.setError("Bạn phải nhập giá tiền");
                }
                else {
                    PhuongTien phuongTien = new PhuongTien(
                            Integer.parseInt(id.getText().toString()),
                            ten.getText().toString(),
                            loai.getText().toString(),
                            Float.parseFloat(gia.getText().toString())
                    );
                    if (dao.them(phuongTien)>0){
                        Toast.makeText(getBaseContext(),"Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                        list.clear();
                        list.addAll(dao.getList());
                        dialog.dismiss();
                    }
                    else id.setError("ID đã tồn tại");
                }


            }
        });
        dialog.show();
    }
    void xoa(){
        Dialog dialog = new Dialog(DanhSachActivity.this);
        dialog.setContentView(R.layout.dialog_xoa_phuong_tien);

        //ánh xạ
        EditText id = dialog.findViewById(R.id.dialog_edt_id);
        Button huy = dialog.findViewById(R.id.dialog_btn_huy);
        Button xoa = dialog.findViewById(R.id.dialog_btn_xoa);

        //Listen
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.getText().toString().equalsIgnoreCase("")){
                    id.setError("Bạn chưa nhập ID");
                }
                else {
                    int res = dao.xoa(id.getText().toString());
                    if (res>0){

                        adapter.notifyDataSetChanged();
                        lengt_list = list.size();
                        list.clear();
                        list.addAll(dao.getList());
                        if (lengt_list == dao.getList().size()){
                            id.setError("Không tồn tại phương tiện có ID: "+id.getText().toString());
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(getBaseContext(), "Đã xóa phương tiện", Toast.LENGTH_LONG).show();
                        }

                    }


                }

            }
        });
        dialog.show();
    }
    void sap_xep(){
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gia.setTextColor(Color.BLACK);
                gia.setText("Giá trị");
                ten.setTextColor(Color.RED);
                a+=1;
                if (a%2==1){
                    ten.setText("Tên ^");

                    adapter.notifyDataSetChanged();
                    list.clear();
                    list.addAll(dao.getNameASC());
                }
                else{
                    ten.setText("Tên v");
                    adapter.notifyDataSetChanged();
                    list.clear();
                    list.addAll(dao.getNameDESC());
                }

            }
        });
        gia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ten.setTextColor(Color.BLACK);
                ten.setText("Tên");
                gia.setTextColor(Color.RED);
                a+=1;
                if (a%2==1){
                    gia.setText("Giá trị ^");
                    adapter.notifyDataSetChanged();
                    list.clear();
                    list.addAll(dao.getTienASC());
                }
                else {
                    gia.setText("Giá trị v");
                    adapter.notifyDataSetChanged();
                    list.clear();
                    list.addAll(dao.getTienDESC());
                }
            }
        });
    }
    void loc(){
        Dialog dialog = new Dialog(DanhSachActivity.this);
        dialog.setContentView(R.layout.dialog_loc);
        EditText min = dialog.findViewById(R.id.edt_min);
        Button ok = dialog.findViewById(R.id.dialog_btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (min.getText().toString().equalsIgnoreCase("")){
                    min.setError("Không được bỏ trống");

                }
                else {
                    Dialog dialog1 = new Dialog(DanhSachActivity.this);
                    dialog1.setContentView(R.layout.dialog_list_loc);
                    RecyclerView rcv = dialog1.findViewById(R.id.dialog_rcv);
                    Button ok = dialog1.findViewById(R.id.dialog_btn_ok);
                    if (dao.getTienMIN(Float.parseFloat(min.getText().toString())).size()==0){
                        Toast.makeText(DanhSachActivity.this,"Không có phương tiện đạt yêu cầu", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        adapter = new PhuongTienAdapter(dao.getTienMIN(Float.parseFloat(min.getText().toString())), DanhSachActivity.this);
                        rcv.setAdapter(adapter);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.dismiss();
                            }
                        });
                        dialog1.show();
                    }
                }
            }
        });
        dialog.show();
    }
    void search(){
        Dialog dialog = new Dialog(DanhSachActivity.this);
        dialog.setContentView(R.layout.dialog_search);
        EditText ten = dialog.findViewById(R.id.dialog_edt_ten);
        Button search = dialog.findViewById(R.id.dialog_btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ten.getText().toString().equalsIgnoreCase("")){
                    ten.setError("Bạn đang để trống tên");
                }
                else {
                    Dialog dialog1 = new Dialog(DanhSachActivity.this);
                    dialog1.setContentView(R.layout.dialog_list_loc);
                    RecyclerView rcv = dialog1.findViewById(R.id.dialog_rcv);
                    Button ok = dialog1.findViewById(R.id.dialog_btn_ok);
                    if (dao.search(ten.getText().toString()).size()==0){
                        Toast.makeText(DanhSachActivity.this, "Không có phương tiện nào có tên: "+ ten.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        adapter = new PhuongTienAdapter(dao.search(ten.getText().toString()), DanhSachActivity.this);
                        rcv.setAdapter(adapter);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.dismiss();
                            }
                        });
                        dialog1.show();
                    }
                }
            }
        });
        dialog.show();
    }
}