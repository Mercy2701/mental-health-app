package com.example.mental_health2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller
public class PythonScriptController {

    @PostMapping("/predict")
    public String predict(@ModelAttribute MentalHealthRequest request,
                          RedirectAttributes redirectAttributes) {

        String result = "";

        try {

            ProcessBuilder pb = new ProcessBuilder(
                    "python",
                    "mental_health_cmd2.py",

                    request.getSelfemployed(),
                    request.getNumEmployees(),
                    request.getTechCompany(),
                    request.getTechRole(),
                    request.getPreviousEmployers(),
                    request.getFamilyHistory(),
                    request.getSoughtTreatment(),
                    String.valueOf(request.getAge()),
                    request.getGender(),
                    request.getAgeGroup(),
                    request.getWorkPosition(),
                    request.getRemoteWork(),
                    request.getOpenAboutMentalHealth()
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            result = reader.readLine(); // ambil 1 line saja

            process.waitFor();

        } catch (Exception e) {
            result = "ERROR";
            e.printStackTrace();
        }

        String status;
        String riskLevel;
        String education;
        String recommendation;

        // =============================
        // FIX LOGIC BERDASARKAN ANGKA
        // =============================
        if ("1".equals(result)) {

            status = "Terindikasi Risiko Gangguan Kesehatan Mental";
            riskLevel = "Tinggi";

            education = "Gangguan kesehatan mental dapat mempengaruhi kesejahteraan dan "
                    + "produktivitas pekerja di industri teknologi. Penting untuk "
                    + "mengenali gejala sejak dini dan mencari dukungan yang tepat.";

            recommendation = "Disarankan untuk berkonsultasi dengan profesional kesehatan "
                    + "mental seperti psikolog atau konselor serta menjaga keseimbangan "
                    + "antara pekerjaan dan kehidupan pribadi.";

        } else {

            status = "Tidak Terindikasi Risiko Gangguan Kesehatan Mental";
            riskLevel = "Rendah";

            education = "Menjaga kesehatan mental merupakan bagian penting dari "
                    + "kesejahteraan individu. Lingkungan kerja yang sehat dan "
                    + "dukungan sosial dapat membantu menjaga kondisi mental tetap stabil.";

            recommendation = "Tetap menjaga pola hidup sehat, manajemen stres, "
                    + "serta komunikasi yang baik dengan rekan kerja dan keluarga.";
        }

        redirectAttributes.addFlashAttribute("result", status);
        redirectAttributes.addFlashAttribute("riskLevel", riskLevel);
        redirectAttributes.addFlashAttribute("education", education);
        redirectAttributes.addFlashAttribute("recommendation", recommendation);

        return "redirect:/result";
    }
}