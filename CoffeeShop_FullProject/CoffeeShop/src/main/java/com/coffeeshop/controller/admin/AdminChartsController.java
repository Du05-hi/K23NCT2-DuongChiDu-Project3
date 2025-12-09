package com.coffeeshop.controller.admin;

import com.coffeeshop.repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminChartsController {

    private final OrderRepository orderRepo;

    public AdminChartsController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    // 📌 view biểu đồ chính
    @GetMapping("/admin/charts")
    public String chartsPage() {
        return "admin/charts"; // gọi charts.html
    }


    // 📈 DATA DOANH THU THEO THÁNG
    @GetMapping("/admin/charts/revenue-data")
    @ResponseBody
    public Object revenueData() {
        return orderRepo.revenueByMonth();
    }


    // 🍩 DATA TRẠNG THÁI ĐƠN HÀNG
    @GetMapping("/admin/charts/order-status-data")
    @ResponseBody
    public int[] statusData() {

        int completed = orderRepo.getSuccessOrders() != null ? orderRepo.getSuccessOrders().intValue() : 0;
        int pending   = orderRepo.getPendingOrders() != null ? orderRepo.getPendingOrders().intValue() : 0;
        int cancelled = orderRepo.getCancelOrders() != null ? orderRepo.getCancelOrders().intValue() : 0;

        return new int[]{completed, pending, cancelled};
    }
}
