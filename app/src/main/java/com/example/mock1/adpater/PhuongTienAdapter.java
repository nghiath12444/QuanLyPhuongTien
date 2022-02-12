package com.example.mock1.adpater;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mock1.PhuongTien;
import com.example.mock1.PhuongTienDAO;
import com.example.mock1.R;

import java.util.List;

public class PhuongTienAdapter extends RecyclerView.Adapter<PhuongTienAdapter.PhuongTienHolder>{
    List<PhuongTien> list;
    Context context;
    PhuongTienDAO dao;
    public PhuongTienAdapter(List<PhuongTien> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new PhuongTienDAO(context);
    }

    @NonNull
    @Override
    public PhuongTienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PhuongTienHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row, parent, false);
        holder = new PhuongTienHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhuongTienHolder holder, int i) {
        PhuongTien phuongTien = list.get(i);
        holder.id.setText(phuongTien.getId()+"");
        holder.ten.setText(phuongTien.getTen());
        holder.loai.setText(phuongTien.getLoai());
        holder.gia.setText("$"+phuongTien.getTien());
        holder.stt.setText((i+1)+"");
        if (i%2==0){
            holder.csl.setBackgroundColor(Color.YELLOW);
        }
        else holder.csl.setBackgroundColor(Color.GREEN);

        holder.csl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_thong_tin_phuong_tien(phuongTien, i);
            }
        });
        holder.csl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_them_sua_phuongtien);

                //anh xa
                EditText id, ten, loai, gia;
                Button huy, sua;
                TextView title;
                id = dialog.findViewById(R.id.edt_id);
                ten = dialog.findViewById(R.id.edt_ten);
                loai =dialog.findViewById(R.id.edt_loai);
                gia = dialog.findViewById(R.id.edt_gia);
                huy = dialog.findViewById(R.id.btn_huy);
                sua = dialog.findViewById(R.id.btn_them_sua);
                title = dialog.findViewById(R.id.titler);

                //settext
                id.setEnabled(false);
                id.setText(phuongTien.getId()+"");
                id.setTextColor(Color.RED);
                ten.setText(phuongTien.getTen());
                loai.setText(phuongTien.getLoai());
                gia.setText(phuongTien.getTien()+"");
                title.setText("CẬP NHẬT");
                sua.setText("Sửa");

                //Listen
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ten.getText().toString().equalsIgnoreCase("")){
                            ten.setError("Bạn phải nhập tên phương tiện");
                        }
                        else if (loai.getText().toString().equalsIgnoreCase("")){
                            loai.setError("Bạn phải nhập loại phương tiện");
                        }
                        else if (gia.getText().toString().equalsIgnoreCase("")){
                            gia.setError("Bạn phải nhập giá tiền");
                        }
                        else {

                            if (dao.sua(phuongTien.getId()+"",
                                    ten.getText().toString(),
                                    loai.getText().toString(),
                                    Float.parseFloat(gia.getText().toString()))>0){
                                Toast.makeText(context,"Cập nhật dữ liệu thành công", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                                list.clear();
                                list.addAll(dao.getList());
                                dialog.dismiss();
                            }

                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    void dialog_thong_tin_phuong_tien(PhuongTien phuongTien, int position){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_thong_tin_phuong_tien);

        //anh xa
        TextView id, ten, loai, gia;
        Button xoa;
        xoa = dialog.findViewById(R.id.btn_xoa);
        id = dialog.findViewById(R.id.tv_id);
        ten = dialog.findViewById(R.id.tv_ten);
        loai = dialog.findViewById(R.id.tv_loai);
        gia = dialog.findViewById(R.id.tv_gia);
        id.setText(phuongTien.getId()+"");
        ten.setText(phuongTien.getTen());
        loai.setText(phuongTien.getLoai());
        gia.setText(phuongTien.getTien()+"");

        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PhuongTienHolder extends RecyclerView.ViewHolder {
        TextView id, ten, loai, gia, stt;
        ConstraintLayout csl;
        public PhuongTienHolder(@NonNull View view) {
            super(view);
            id = view.findViewById(R.id.tv_id);
            ten = view.findViewById(R.id.tv_ten);
            loai = view.findViewById(R.id.tv_loai);
            gia = view.findViewById(R.id.tv_gia);
            stt = view.findViewById(R.id.stt);
            csl = view.findViewById(R.id.ctl);
        }
    }
}
