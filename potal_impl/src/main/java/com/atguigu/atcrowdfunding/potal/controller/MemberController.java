package com.atguigu.atcrowdfunding.potal.controller;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.potal.listener.PassListener;
import com.atguigu.atcrowdfunding.potal.listener.RefuseListener;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;


@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CertService certService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @RequestMapping("/index")
    public String index()
    {
        return "member/index";
    }

    @RequestMapping("/checkmail")
    public String checkmail()
    {
        return "member/checkmail";
    }

    @RequestMapping("/apply")
    public String apply(HttpSession session)
    {
        Member loginMember=(Member)session.getAttribute(Const.LOGIN_MEMBER);
        Ticket ticket=ticketService.queryTicketByMemberid(loginMember.getId());
        if(ticket==null)
        {
            ticket=new Ticket();
            ticket.setMemberid(loginMember.getId());
            ticket.setStatus("0");
            ticket.setPstep("apply");
        }else {
            String pstep=ticket.getPstep();
            if("accttype".equals(pstep))
            {
                return "redirect:/member/basicinfo.htm";
            }else if("basicinfo".equals(pstep))
            {
                //根据当前用户查询账户类型，然后根据账户类型查询需要上传的资质
                List<Cert> queryCertByAcctType=certService.queryCertByAcctType(loginMember.getAccttype());
                session.setAttribute("queryCertByAcctType",queryCertByAcctType);
                return "redirect:/member/uploadCert.htm";
            }else if("uploadcert".equals(pstep))
            {
                return "redirect:/member/checkmail.htm";
            }else if("checkmail".equals(pstep))
            {
                return "redirect:/member/checkauthcode.htm";
            }
        }
        return "member/accttype";
    }

    @RequestMapping("/updateAcctType")
    @ResponseBody
    public Object updateAcctType(HttpSession session, String accttype)
    {
        AjaxResult result=new AjaxResult();
        try{
           Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
           member.setAccttype(accttype);
           int count=memberService.updateAcctType(member);

           //更新ticket
            Ticket ticket=ticketService.queryTicketByMemberid(member.getId());
            ticket.setPstep("accttype");
            ticketService.updateTicketProcessPstep(ticket);

           result.setSuccess(true);
        }catch (Exception e)
        {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @RequestMapping("/basicinfo")
    public String basicinfo()
    {
        return "member/basicinfo";
    }

    @RequestMapping("/checkauthcode")
    public String checkauthcode()
    {
        return "/member/checkauthcode";
    }

    @RequestMapping("/updateBasicInfo")
    @ResponseBody
    public Object updateBasicInfo(HttpSession session,Member member)
    {
        AjaxResult result=new AjaxResult();
        try{
            Member loginMember=(Member)session.getAttribute(Const.LOGIN_MEMBER);

            //修改ticket的内容,更新当前流程步骤
            Ticket ticket=ticketService.queryTicketByMemberid(loginMember.getId());
            ticket.setPstep("basicinfo");
            ticketService.updateTicketProcessPstep(ticket);

            loginMember.setCardnum(member.getCardnum());
            loginMember.setRealname(member.getRealname());
            loginMember.setTel(member.getTel());
            int count=memberService.updateMember(loginMember);
            result.setSuccess(count==1);
        }catch (Exception e)
        {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @RequestMapping("/uploadCert")
    public String uploadCert(HttpSession session)
    {
        Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
        List<Cert> queryCertByAcctType=certService.queryCertByAcctType(loginMember.getAccttype());
        session.setAttribute("queryCertByAcctType",queryCertByAcctType);

        return "member/uploadCert";
    }

    @RequestMapping("/doUploadCert")
    @ResponseBody
    public Object doUploadCert(Data data,HttpSession session)
    {
        AjaxResult result=new AjaxResult();
        try{
            ServletContext application=session.getServletContext();
            String realPath = application.getRealPath("/pics");//获取绝对路径

            Member loginMember=(Member)session.getAttribute(Const.LOGIN_MEMBER);
            for(MemberCert memberCert:data.getCertimgs())
            {
                memberCert.setMemberid(loginMember.getId());
                MultipartFile file=memberCert.getFileImg();
                String oldFileName=file.getOriginalFilename();
                String fileName= UUID.randomUUID().toString()+oldFileName.substring(oldFileName.lastIndexOf("."));
                memberCert.setIconpath(fileName);
                String filePath=realPath+"/cert"+"/"+fileName;
                file.transferTo(new File(filePath));
            }

            List<MemberCert> certimgs = data.getCertimgs();
            memberService.insertMemberCert(certimgs);

            //更新ticket
            Ticket ticket=ticketService.queryTicketByMemberid(loginMember.getId());
            ticket.setPstep("uploadcert");
            ticketService.updateTicketProcessPstep(ticket);

            result.setSuccess(true);


        }catch (Exception e)
        {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @RequestMapping("/startProcess")
    @ResponseBody
    public Object startProcess(HttpSession session,String email){
        AjaxResult result =new AjaxResult();
        try{
            Member loginMember=(Member) session.getAttribute(Const.LOGIN_MEMBER);

            if(!email.equals(loginMember.getEmail()))
            {
                loginMember.setEmail(email);
                memberService.updateEmail(loginMember);
            }
            //启动实名认证流程，系统自动发送邮件，生成验证码，验证邮箱是否合法
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("auth").singleResult();


            StringBuilder authcode =new StringBuilder();
            for(int i=0;i<4;i++)
            {
                authcode.append(new Random().nextInt(10));
            }
             Map<String,Object> variables=new HashMap<>();
             variables.put("toEmail",email);
             variables.put("authcode",authcode.toString());
             variables.put("loginacct",loginMember.getLoginacct());
             variables.put("passListener",new PassListener());
             variables.put("refuseListener",new RefuseListener());

            ProcessInstance processInstance=runtimeService.startProcessInstanceById(processDefinition.getId(),variables);
            Ticket ticket=ticketService.queryTicketByMemberid(loginMember.getId());
            ticket.setPstep("checkmail");
            ticket.setPiid(processInstance.getId());
            ticket.setAuthcode(authcode.toString());
            ticketService.updateTicket(ticket);
            result.setSuccess(true);
        }catch (Exception e)
        {
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/finishApply")
    @ResponseBody
    public Object finish(String authcode,HttpSession session)
    {
        AjaxResult result=new AjaxResult();

        try{
            Member loginMember=(Member) session.getAttribute(Const.LOGIN_MEMBER);
            //让当前用户完成验证码审核功能
            Ticket ticket=ticketService.queryTicketByMemberid(loginMember.getId());
            if(ticket.getAuthcode().equals(authcode))
            {
                //完成审核验证码任务
                Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid()).
                        taskAssignee(loginMember.getLoginacct()).singleResult();
                taskService.complete(task.getId());

                loginMember.setAuthstatus("1");
                memberService.updateAuthstatus(loginMember);

                ticket.setPstep("finishApply");
                ticketService.updatePstep(ticket);
                result.setSuccess(true);
            }else {
                result.setSuccess(false);
                result.setMessage("验证码输入错误！！！");
            }
        }catch (Exception e)
        {
            result.setSuccess(false);
        }

        return result;
    }
}
