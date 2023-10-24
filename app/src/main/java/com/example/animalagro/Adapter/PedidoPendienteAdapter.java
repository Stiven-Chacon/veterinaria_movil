package com.example.animalagro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalagro.R;
import com.example.animalagro.data.PedidoPendienteAdmin;

import java.util.List;

public class PedidoPendienteAdapter extends RecyclerView.Adapter<PedidoPendienteAdapter.PedidoViewHolder> {

    private List<PedidoPendienteAdmin> pedidos;
    private Context context;

    public PedidoPendienteAdapter(List<PedidoPendienteAdmin> pedidos) {
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.viewholder_pedidos_pendientes_list, parent, false);
        return new PedidoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        PedidoPendienteAdmin pedido = pedidos.get(position);

        holder.txtFechaPedidoPendientes.setText("Fecha: " + pedido.getPeFecha());
        holder.txtCodigoPedidoPendiente.setText("Codigo: " + pedido.getPeCodigo());

        // Configurar el botón para abrir la URL del PDF
        holder.btnPdfPedidoPendiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlPdfPedido = pedido.getUrlPedido();
                if (urlPdfPedido != null && !urlPdfPedido.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlPdfPedido));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView txtFechaPedidoPendientes;
        TextView txtCodigoPedidoPendiente;
        Button btnPdfPedidoPendiente;

        public PedidoViewHolder(View itemView) {
            super(itemView);
            txtFechaPedidoPendientes = itemView.findViewById(R.id.txtFechaPedidoPendientes);
            txtCodigoPedidoPendiente = itemView.findViewById(R.id.txtCodigoPedidoPendiente);
            btnPdfPedidoPendiente = itemView.findViewById(R.id.btnPdfPedidoPendiente);
        }
    }
}