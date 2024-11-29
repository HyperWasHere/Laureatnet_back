package com.eheiste.laureatnet.config;

import com.eheiste.laureatnet.model.*;
import com.eheiste.laureatnet.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer {
    private final AttachmentRepository attachmentRepository;
    private final ScientificArticleRepository scientificArticleRepository;
    private final EntrepriseRepository entrepriseRepository;
    private UserAccountRepository accountRepository;
    private AccountTypeRepository accountTypeRepository;
    private UserProfileRepository userProfileRepository;
    private UserSettingsRepository userSettingsRepository;
    private SectionTypeRepository sectionTypeRepository;

    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        AccountType userType = AccountType.builder().id(1L).title("User").roles("USER").build();
        AccountType laureatType = AccountType.builder().id(2L).title("Laureat").roles("USER, LAUREAT").build();
        AccountType docType = AccountType.builder().id(4L).title("Doc").roles("USER, LAUREAT, DOC").build();
        AccountType adminType = AccountType.builder().id(3L).title("Admin").roles("USER, LAUREAT, DOC, ADMIN").build();

        if (accountTypeRepository.findAll().size() < 4) {
            userType = accountTypeRepository.save(userType);
            laureatType = accountTypeRepository.save(laureatType);
            docType = accountTypeRepository.save(docType);
            adminType = accountTypeRepository.save(adminType);
        }

        if (sectionTypeRepository.findAll().size() != 3) {
            SectionType experience = SectionType.builder().title("Experiences").build();
            sectionTypeRepository.save(experience);
            SectionType education = SectionType.builder().title("Education").build();
            sectionTypeRepository.save(education);
            SectionType projet = SectionType.builder().title("Projets").build();
            sectionTypeRepository.save(projet);
        }

        if (accountRepository.findAll().size() < 4) {

            UserProfile userProfile1 = UserProfile.builder()
                    .firstName("mohammed")
                    .lastName("abad")
                    .banner("https://picsum.photos/1400/400")
                    .picture("https://picsum.photos/200/200")
                    //.userAccount(userAccount1)
                    .build();
            //userProfileRepository.save(userProfile1);

            UserSetting userSetting1 = UserSetting.builder()
            		.acceptMsgs(false)
            		.administrativePosts(false)
            		.privateMsgs(false)
            		.connectionRequest(true)
                    //.userAccount(userAccount1)
                    .build();
            UserAccount userAccount1 = UserAccount.builder()
                    .email("med@gmail.com")
                    .password(passwordEncoder.encode("123"))
                    .isEnabled(true)
                    .accountType(userType)
                    .userProfile(userProfile1)
                    .userSettings(userSetting1)
                    .build();
            userAccount1 = accountRepository.save(userAccount1);            //userSettingsRepository.save(userSetting1);

            UserProfile userProfile2 = UserProfile.builder()
                    .firstName("ahmed")
                    .lastName("debbab")
                    .banner("https://picsum.photos/1400/400")
                    .picture("https://picsum.photos/200/200")
                    //.userAccount(userAccount2)
                    .build();
            //userProfileRepository.save(userProfile2);

            UserSetting userSetting2 = UserSetting.builder()
            		.acceptMsgs(false)
            		.administrativePosts(false)
            		.privateMsgs(false)
            		.connectionRequest(true)
                    //.userAccount(userAccount2)
                    .build();
            //userSettingsRepository.save(userSetting2);
            UserAccount userAccount2 = UserAccount.builder()
                    .email("ahmed@gmail.com")
                    .password(passwordEncoder.encode("123"))
                    .isEnabled(true)
                    .accountType(laureatType)
                    .userSettings(userSetting2)
                    .userProfile(userProfile2)
                    .build();
            userAccount2 = accountRepository.save(userAccount2);


            UserProfile userProfile3 = UserProfile.builder()
                    .firstName("ayoub")
                    .lastName("hilmi")
                    .banner("https://picsum.photos/1400/400")
                    .picture("https://picsum.photos/200/200")
                    //.userAccount(userAccount3)
                    .build();
            //userProfileRepository.save(userProfile3);

            UserSetting userSetting3 = UserSetting.builder()
            		.acceptMsgs(false)
            		.administrativePosts(false)
            		.privateMsgs(false)
            		.connectionRequest(true)
            		//.userAccount(userAccount3)
                    .build();
            //userSettingsRepository.save(userSetting3);
            UserAccount userAccount3 = UserAccount.builder()
                    .email("ayoub@mail.ml")
                    .password(passwordEncoder.encode("0000"))
                    .isEnabled(true)
                    .accountType(adminType)
                    .userProfile(userProfile3)
                    .userSettings(userSetting3)
                    .build();
            userAccount3 = accountRepository.save(userAccount3);


            UserProfile userProfile4 = UserProfile.builder()
                    .firstName("s")
                    .lastName("douhi")
                    .banner("https://picsum.photos/1400/400")
                    .picture("https://picsum.photos/200/200")
                    //.userAccount(userAccount4)
                    .build();
            //userProfileRepository.save(userProfile4);

            UserSetting userSetting4 = UserSetting.builder()
                    //.userAccount(userAccount4)
            		.acceptMsgs(false)
            		.administrativePosts(false)
            		.privateMsgs(false)
            		.connectionRequest(true)
            		.build();
            //userSettingsRepository.save(userSetting4);
            UserAccount userAccount4 = UserAccount.builder()
                    .email("salsabil@gmail.com")
                    .password(passwordEncoder.encode("test"))
                    .isEnabled(true)
                    .accountType(docType)
                    .userProfile(userProfile4)
                    .userSettings(userSetting4)
                    .build();
            userAccount4 = accountRepository.save(userAccount4);


        }

        // migrate from old paths to new paths
        List<Attachment> attachments = attachmentRepository.findAll();
        attachments.forEach(a -> {
            if (a.getPath().contains("http://localhost:8080/api/v1/files/")) {
                a.setPath(a.getPath().replace("http://localhost:8080/api/v1/files/", ""));
                attachmentRepository.save(a);
            }
        });

//        List<UserProfile> userProfiles = userProfileRepository.findAll();
//        userProfiles.forEach(a -> {
//            if (a.getBanner().contains("http://localhost:8080/api/v1/files/") || a.getPicture().contains("http://localhost:8080/api/v1/files/")) {
//                a.setBanner(a.getBanner().replace("http://localhost:8080/api/v1/files/", ""));
//                a.setPicture(a.getPicture().replace("http://localhost:8080/api/v1/files/", ""));
//                userProfileRepository.save(a);
//            }
//        });

//        List<ScientificArticle> scientificArticles = scientificArticleRepository.findAll();
//        scientificArticles.forEach(a -> {
//            if (a.getPath().contains("http://localhost:8080/api/v1/files/")) {
//                a.setPath(a.getPath().replace("http://localhost:8080/api/v1/files/", ""));
//                scientificArticleRepository.save(a);
//            }
//        });

//        List<Entreprise> entreprises = entrepriseRepository.findAll();
//        entreprises.forEach(a -> {
//            if (a.getLogo().contains("http://localhost:8080/api/v1/files/")) {
//                a.setLogo(a.getLogo().replace("http://localhost:8080/api/v1/files/", ""));
//                entrepriseRepository.save(a);
//            }
//        });

    }
}
