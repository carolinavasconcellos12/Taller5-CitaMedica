package com.example.citamedica.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.citamedica.Models.Appointment
import com.example.citamedica.R


class AppointmentAdapter(private val appointments: ArrayList<Appointment>) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvAppointmentId = itemView.findViewById<TextView>(R.id.tv_id)
        val tvDoctorName = itemView.findViewById<TextView>(R.id.tv_medico)
        val tvScheduledDate = itemView.findViewById<TextView>(R.id.tv_fecha)
        val tvScheduledTime = itemView.findViewById<TextView>(R.id.tv_hora)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)

        )
    }

    override fun getItemCount() = appointments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]

        holder.tvAppointmentId.text = "Cita Medica:"
        holder.tvDoctorName.text = appointment.doctorName
        holder.tvScheduledDate.text = "Atención el día  ${appointment.scheduledDate}"
        holder.tvScheduledTime.text = "A las ${appointment.scheduledTime}"

    }


}