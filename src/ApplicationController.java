import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.DayOfWeek;
import javafx.scene.paint.Color;
import javax.swing.*;
import javafx.embed.swing.SwingNode;


public class ApplicationController {
    
    private JInternalFrame frame;
    private JDesktopPane DPane;

    @FXML
    private Text Button_Booking;

    @FXML
    private Text Button_Dashborad;

    @FXML
    private Text Button_Logout;

    @FXML
    private Button Button_Next;

    @FXML
    private Button Button_Prev;

    @FXML
    private Text Button_Profile;

    @FXML
    private Button Button_Quit;

    @FXML
    private Text Button_Service;

    @FXML
    private ImageView Logo;

    @FXML
    private AnchorPane PageBookingService;

    @FXML
    private AnchorPane PageDashborad;

    @FXML
    private AnchorPane PageProfile;

    @FXML
    private AnchorPane PageServiceMenu;
    
    @FXML
    private AnchorPane desktopPaneContainer;

    @FXML private Text d0_0, d0_1, d0_2, d0_3, d0_4, d0_5, d0_6;
    @FXML private Text d1_0, d1_1, d1_2, d1_3, d1_4, d1_5, d1_6;
    @FXML private Text d2_0, d2_1, d2_2, d2_3, d2_4, d2_5, d2_6;
    @FXML private Text d3_0, d3_1, d3_2, d3_3, d3_4, d3_5, d3_6;
    @FXML private Text d4_0, d4_1, d4_2, d4_3, d4_4, d4_5, d4_6;
    @FXML private Text d5_0, d5_1, d5_2, d5_3, d5_4, d5_5, d5_6;
    
    @FXML
    private Text[][] dayTexts = new Text[6][7];
    
    private YearMonth currentYearMonth = YearMonth.now();
    
    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    public void setCurrentYearMonth(YearMonth currentYearMonth) {
        this.currentYearMonth = currentYearMonth;
    }   
    
    @FXML
    public void initialize() {
        dayTexts[0][0] = d0_0;
        dayTexts[0][1] = d0_1;
        dayTexts[0][2] = d0_2;
        dayTexts[0][3] = d0_3;
        dayTexts[0][4] = d0_4;
        dayTexts[0][5] = d0_5;
        dayTexts[0][6] = d0_6;
        dayTexts[1][0] = d1_0;
        dayTexts[1][1] = d1_1;
        dayTexts[1][2] = d1_2;
        dayTexts[1][3] = d1_3;
        dayTexts[1][4] = d1_4;
        dayTexts[1][5] = d1_5;
        dayTexts[1][6] = d1_6;
        dayTexts[2][0] = d2_0;
        dayTexts[2][1] = d2_1;
        dayTexts[2][2] = d2_2;
        dayTexts[2][3] = d2_3;
        dayTexts[2][4] = d2_4;
        dayTexts[2][5] = d2_5;
        dayTexts[2][6] = d2_6;
        dayTexts[3][0] = d3_0;
        dayTexts[3][1] = d3_1;
        dayTexts[3][2] = d3_2;
        dayTexts[3][3] = d3_3;
        dayTexts[3][4] = d3_4;
        dayTexts[3][5] = d3_5;
        dayTexts[3][6] = d3_6;
        dayTexts[4][0] = d4_0;
        dayTexts[4][1] = d4_1;
        dayTexts[4][2] = d4_2;
        dayTexts[4][3] = d4_3;
        dayTexts[4][4] = d4_4;
        dayTexts[4][5] = d4_5;
        dayTexts[4][6] = d4_6;
        dayTexts[5][0] = d5_0;
        dayTexts[5][1] = d5_1;
        dayTexts[5][2] = d5_2;
        dayTexts[5][3] = d5_3;
        dayTexts[5][4] = d5_4;
        dayTexts[5][5] = d5_5;
        dayTexts[5][6] = d5_6;
        
        updateCalendar();
        
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                final Text dayText = dayTexts[row][col];
                dayText.setOnMouseClicked(event -> DayClick(dayText));
            }
        }
    }
    
    private void updateCalendar() {
        
        YearMonth yearMonth = getCurrentYearMonth();
        LocalDate firstOfMonth = yearMonth.atDay(1);
        DayOfWeek firstDayOfWeek = firstOfMonth.getDayOfWeek();
        int firstDayOfWeekValue = firstDayOfWeek.getValue();

        int startIndex = firstDayOfWeekValue % 7; 
        int dayOfMonth = 1;

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                int index = row * 7 + col;
                if (index >= startIndex && dayOfMonth <= yearMonth.lengthOfMonth()) {
                    dayTexts[row][col].setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                } else {
                    dayTexts[row][col].setText("");
                }
            }
        }
    }
    
    private void DayClick(Text dayText) {
    String day = dayText.getText();
    if (!day.isEmpty()) {
        SwingUtilities.invokeLater(() -> {
            frame = new JInternalFrame("Day X", true, true, true, true);
            DPane = new JDesktopPane();
            frame.setSize(300, 500);
            DPane.add(frame);
            frame.setVisible(true);
            DPane.setVisible(true);
            
            
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(DPane);
            desktopPaneContainer.getChildren().add(swingNode);
        });
    } else {
        System.out.println("Cannot click on empty date");
        }
    }
    
    @FXML
    void ShowNextMonth(MouseEvent event) {
        YearMonth nextMonth = currentYearMonth.plusMonths(1);
        setCurrentYearMonth(nextMonth);
        updateCalendar();
    }

    @FXML
    void ShowPrevMonth(MouseEvent event) {
        YearMonth previousMonth = currentYearMonth.minusMonths(1);
        setCurrentYearMonth(previousMonth);
        updateCalendar();
    }
    
    @FXML
    public void TextOnMouseEntered(MouseEvent event) {
        if (event.getSource() == Button_Profile) {
            Button_Profile.setFill(Color.web("#A1E3F9"));
        } if (event.getSource() == Button_Booking) {
            Button_Booking.setFill(Color.web("#A1E3F9"));
        } if (event.getSource() == Button_Service) {
            Button_Service.setFill(Color.web("#A1E3F9"));
        } if (event.getSource() == Button_Dashborad) {
            Button_Dashborad.setFill(Color.web("#A1E3F9"));
        } if (event.getSource() == Button_Logout) {
            Button_Logout.setFill(Color.web("#A1E3F9"));
        }
    }

    @FXML
    public void TextMouseExited(MouseEvent event) {
        if (event.getSource() == Button_Profile) {
            Button_Profile.setFill(Color.web("#3674b5"));
        } if (event.getSource() == Button_Booking) {
            Button_Booking.setFill(Color.web("#3674b5"));
        } if (event.getSource() == Button_Service) {
            Button_Service.setFill(Color.web("#3674b5"));
        } if (event.getSource() == Button_Dashborad) {
            Button_Dashborad.setFill(Color.web("#3674b5"));
        } if (event.getSource() == Button_Logout) {
            Button_Logout.setFill(Color.web("#3674b5"));
        }
    }

    @FXML
    void SwitchToBooking(MouseEvent event) {
        PageProfile.setVisible(false);
        PageBookingService.setVisible(true);
        PageServiceMenu.setVisible(false);
        PageDashborad.setVisible(false);
    }

    @FXML
    void SwitchToDashborad(MouseEvent event) {
        PageProfile.setVisible(false);
        PageBookingService.setVisible(false);
        PageServiceMenu.setVisible(false);
        PageDashborad.setVisible(true);
    }

    @FXML
    void SwitchToProfile(MouseEvent event) {
        PageProfile.setVisible(true);
        PageBookingService.setVisible(false);
        PageServiceMenu.setVisible(false);
        PageDashborad.setVisible(false);
    }

    @FXML
    void SwitchToService(MouseEvent event) {
        PageProfile.setVisible(false);
        PageBookingService.setVisible(false);
        PageServiceMenu.setVisible(true);
        PageDashborad.setVisible(false);
    }

}
