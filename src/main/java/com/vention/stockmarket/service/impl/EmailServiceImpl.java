package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.domain.SecurityModel;
import com.vention.stockmarket.dto.response.CompanyInfoResponseDTO;
import com.vention.stockmarket.feign.clients.StockServiceFeign;
import com.vention.stockmarket.repository.FavouriteCompaniesRepository;
import com.vention.stockmarket.repository.SecurityRepository;
import com.vention.stockmarket.service.EmailService;
import com.vention.stockmarket.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final FavouriteCompaniesRepository favouriteCompaniesRepository;
    private final SecurityRepository securityRepository;
    private final StockService stockService;
    @Value("${spring.mail.username}")
    private String sender;


    @Override
    @Scheduled(cron = "0 24 13 * * ?")
    public void sendEmailToUsersAboutFavouriteCompanies() {
        securityRepository.getAll().forEach(securityModel -> {
            var email = securityModel.getEmail();

            var companiesInfo = favouriteCompaniesRepository
                    .findByUserId(securityModel.getUserId())
                    .stream().map(symbol -> stockService.getLatestCompanyInfo(symbol))
                    .toList();

            sendEmailToUser(email, companiesInfo);
        });

    }

    public void sendEmailToUser(String userEmail, List<CompanyInfoResponseDTO> companyInfo) {
        SimpleMailMessage mailMessage
                = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Detailed info about your favourite companies");
        mailMessage.setText(makeEmailBody(companyInfo));

        javaMailSender.send(mailMessage);
    }

    public String makeEmailBody(List<CompanyInfoResponseDTO> companies) {
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<html><body>");

        // Iterate through the list of companies and add their information to the email body
        for (CompanyInfoResponseDTO companyInfo : companies) {
            emailBody.append("<h2>").append(companyInfo.getName()).append("</h2>");
            emailBody.append("<p><b>Symbol:</b> ").append(companyInfo.getSymbol()).append("</p>");
            emailBody.append("<p><b>Exchange:</b> ").append(companyInfo.getExchange()).append("</p>");
            emailBody.append("<p><b>MIC Code:</b> ").append(companyInfo.getMixCode()).append("</p>");
            emailBody.append("<p><b>Sector:</b> ").append(companyInfo.getSector()).append("</p>");
            emailBody.append("<p><b>Industry:</b> ").append(companyInfo.getIndustry()).append("</p>");
            emailBody.append("<p><b>Employees:</b> ").append(companyInfo.getEmployees()).append("</p>");
            emailBody.append("<p><b>Website:</b> <a href=\"").append(companyInfo.getWebsite()).append("\">").append(companyInfo.getWebsite()).append("</a></p>");
            emailBody.append("<p><b>Description:</b> ").append(companyInfo.getDescription()).append("</p>");
            emailBody.append("<p><b>Type:</b> ").append(companyInfo.getType()).append("</p>");
            emailBody.append("<p><b>CEO:</b> ").append(companyInfo.getCeo()).append("</p>");
            emailBody.append("<p><b>Address:</b> ").append(companyInfo.getAddress()).append("</p>");
            emailBody.append("<p><b>City:</b> ").append(companyInfo.getCity()).append("</p>");
            emailBody.append("<p><b>Zip:</b> ").append(companyInfo.getZip()).append("</p>");
            emailBody.append("<p><b>State:</b> ").append(companyInfo.getState()).append("</p>");
            emailBody.append("<p><b>Country:</b> ").append(companyInfo.getCountry()).append("</p>");
            emailBody.append("<p><b>Phone:</b> ").append(companyInfo.getPhone()).append("</p>");
            emailBody.append("<hr>");
        }

        emailBody.append("</body></html>");
        return emailBody.toString();
    }
}
