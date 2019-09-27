package com.atguigu.atcrowdfunding.potal.service;

import com.atguigu.atcrowdfunding.bean.Ticket;

public interface TicketService {
    Ticket queryTicketByMemberid(Integer id);

    void updateTicketProcessPstep(Ticket ticket);
}
