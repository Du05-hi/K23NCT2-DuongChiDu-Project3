package com.coffeeshop.controller.admin;

import com.coffeeshop.repository.OrderRepository;
import com.coffeeshop.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminDashboardController {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;   // Thêm khai báo userRepo

    public AdminDashboardController(OrderRepository orderRepo,
                                    UserRepository userRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;  // Gán vào constructor
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalRevenue", orderRepo.getTotalRevenue());
        model.addAttribute("totalOrders", orderRepo.getTotalOrders());
        model.addAttribute("successOrders", orderRepo.getSuccessOrders());
        model.addAttribute("pendingOrders", orderRepo.getPendingOrders());
        model.addAttribute("cancelOrders", orderRepo.getCancelOrders());

        // ⭐⭐⭐ Thêm thống kê số user
        model.addAttribute("totalUsers", userRepo.countUsers());

        return "admin/dashboard";
    }

}
