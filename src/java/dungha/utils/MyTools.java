package dungha.utils;

import dungha.answer.Answer;
import dungha.answer.AnswerDAO;
import dungha.question.Question;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

public class MyTools implements Serializable {

    public static String encodePassword(String password) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytesEncode = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        String passEncode = Base64.getEncoder().encodeToString(bytesEncode);

        return passEncode;
    }

    public static boolean parseStatus(String statusPara) {

        return statusPara.equals(Assets.ACTIVE);
    }

    public static int getOffSet(int currentPage, int numRecordInPage) {
        return (currentPage - 1) * numRecordInPage;
    }

    public static int getMaxPage(int totalRecord, int numRecordInPage) {
        return (int) Math.ceil((totalRecord * 1.0) / numRecordInPage);
    }

    public static int getCurrentPage(String currentPagePara, int maxPage) {
        int currentPage;

        if (currentPagePara != null) {
            currentPage = Integer.parseInt(currentPagePara);
            currentPage = currentPage > maxPage ? maxPage : currentPage;
            currentPage = currentPage < 1 ? 1 : currentPage;
        } else {
            currentPage = 1;
        }

        return currentPage;
    }

    public static Map<Integer, List<Answer>> getListAnswer(List<Question> listQuestion) throws SQLException, NamingException {
        AnswerDAO answerDAO = new AnswerDAO();
        Map<Integer, List<Answer>> listAnswer = new HashMap<>();

        for (Question question : listQuestion) {
            List<Answer> answer = answerDAO.getByQuestionID(question.getQuestionID());

            for (Answer ans : answer) {
                if (ans.getAnswerContent().contains("<")) {
                    String buff = ans.getAnswerContent();
                    buff = buff.replace("<", "&lsaquo;");
                    ans.setAnswerContent(buff);
                }
            }

            listAnswer.put(question.getQuestionID(), answer);
        }

        return listAnswer;
    }

    public static void replaceString(List<Question> listQuestion) {
        for (Question question : listQuestion) {
            if (question.getQuestionContent().contains("<")) {
                String buff = question.getQuestionContent();
                buff = buff.replace("<", "&lsaquo;");
                question.setQuestionContent(buff);
            }
        }
    }
}
