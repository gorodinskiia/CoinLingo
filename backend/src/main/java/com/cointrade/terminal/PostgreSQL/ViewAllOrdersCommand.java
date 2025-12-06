package com.cointrade.terminal.PostgreSQL;
import org.springframework.ui.Model;

public class ViewAllOrdersCommand implements Command {

    private OrderRepository orderRepository;
    private Model model;

    public ViewAllOrdersCommand(OrderRepository orderRepository, Model model) {
        this.orderRepository = orderRepository;
        this.model = model;
    }

    public ViewAllOrdersCommand() {
        // Constructor can be expanded to include dependencies if needed
    }

    @Override
    public void execute() {
        // Fetch and display all orders
        System.out.println("Viewing all orders...");
    }
}
