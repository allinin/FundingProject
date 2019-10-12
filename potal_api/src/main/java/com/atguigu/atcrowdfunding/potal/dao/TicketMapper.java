package com.atguigu.atcrowdfunding.potal.dao;

import com.atguigu.atcrowdfunding.bean.Ticket;

public interface TicketMapper{
    Ticket queryTicketByMemberid(Integer id);

    void updateTicketProcessPstep(Ticket ticket);
    void updateTicket(Ticket ticket);

    void updatePstep(Ticket ticket);
}
