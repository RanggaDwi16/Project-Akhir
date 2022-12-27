package com.example.tugasakhiruas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private Context context;
    private ArrayList<Model> arrayList;
    Database databaseHelper;

    // Constructor
    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        databaseHelper = new Database(context);
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView profile;
        TextView nama, tanggal, detail;
        ImageButton editRow, delete;

        public Holder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.mempelai);
            nama = itemView.findViewById(R.id.nama);
            tanggal = itemView.findViewById(R.id.tanggal);
            editRow = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            detail = itemView.findViewById(R.id.Detail);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_view, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {

        Model model = arrayList.get(position);
        final String id = model.getId();
        final String image = model.getImage();
        final String nama = model.getNama();
        final String tanggal = model.getTanggal();
        final String addTimeStamp = model.getAddTimeStamp();
        final String updateTimeStamp = model.getUpdatetimeStamp();

        holder.profile.setImageURI(Uri.parse(image));
        holder.nama.setText(nama);
        holder.tanggal.setText(tanggal);

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
            Intent dataform = new Intent(view.getContext(), DetailUndangan.class);
            dataform.putExtra("SendKode", holder.nama.getText().toString());
            context.startActivity(dataform);
                ((Activity)context).finish();
            }
        });

        holder.editRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editColumn(
                        ""+position,
                        ""+id,
                        ""+image,
                        ""+nama,
                        ""+tanggal,
                        ""+addTimeStamp,
                        ""+updateTimeStamp
                );
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog(
                        ""+id
                );
            }
        });
    }

    private void deleteDialog(String id){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Hapus");
        builder.setMessage("Apakah kamu ingin menghapus?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseHelper.deleteInfo(id);
                ((HomeActivity)context).onResume();
                Toast.makeText(context, "Hapus Data Sukses!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.create().show();
    }

    private void editColumn(String position, String id, String image, String nama, String tanggal, String addTimeStamp, String updateTimeStamp) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update");
        builder.setMessage("Apakah kamu ingin update ?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_baseline_edit_24);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, EditUndangan.class);
                intent.putExtra("ID", id);
                intent.putExtra("IMAGE", image);
                intent.putExtra("NAMA", nama);
                intent.putExtra("TANGGAL", tanggal);
                intent.putExtra("ADD_TIMESTAMP", addTimeStamp);
                intent.putExtra("UPDATE_TIMESTAMP", updateTimeStamp);
                intent.putExtra("editMode", true);
                context.startActivity(intent);

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.create().show();

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
