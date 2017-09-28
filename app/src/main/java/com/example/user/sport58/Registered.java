package com.example.user.sport58;

import android.app.Activity;

import android.content.Intent;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import cn.gavinliu.android.lib.scale.config.ScaleConfig;
import es.dmoral.toasty.Toasty;


public class Registered extends AppCompatActivity {
    private  CheckBox check;
    private  Toast toast;
    private  EditText account_number;
    private  EditText name;
    private  EditText password;
    private  EditText repassword;
    private  EditText realname;
    private  EditText phone;
    private  EditText email;
    private  String account_numberString;
    private  String nameString;
    private  String phoneString;
    private  String passwordString;
    private  String realnameString;
    private  String emailString;
    private  String repasswordString;
    private  int number, pass, phonglength;
    private  TextView number_message, password_message, repassword_message, realname_message, phone_message, email_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //自適屏布局始量化
        ScaleConfig.create(this,
                1080, // Design Width
                1920, // Design Height
                3,    // Design Density
                3,    // Design FontScale
                ScaleConfig.DIMENS_UNIT_DP);
        setContentView(R.layout.activity_registered);







        setTitle("註冊");
        Activity regisclose = this;
        Activity mainclose = this;

        account_number = (EditText) findViewById(R.id.editText);
        name = (EditText) findViewById(R.id.editText2);
        password = (EditText) findViewById(R.id.editText3);
        repassword = (EditText) findViewById(R.id.editText5);
        realname = (EditText) findViewById(R.id.editText4);
        phone = (EditText) findViewById(R.id.editText6);
        email = (EditText) findViewById(R.id.editText7);
        number_message = (TextView) findViewById(R.id.textView3);
        password_message = (TextView) findViewById(R.id.textView5);
        repassword_message = (TextView) findViewById(R.id.textView6);
        phone_message = (TextView) findViewById(R.id.textView7);
        realname_message = (TextView) findViewById(R.id.textView8);
        email_message = (TextView) findViewById(R.id.textView15);
        account_number.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        repassword.addTextChangedListener(textWatcher);
        realname.addTextChangedListener(textWatcher);
        phone.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        Button insert = (Button) findViewById(R.id.prediction);
        Button cancel = (Button) findViewById(R.id.button3);
        Button terms = (Button) findViewById(R.id.button5);
        check = (CheckBox) findViewById(R.id.checkBox);
        Button clear = (Button) findViewById(R.id.button6);

        cancel.setOnClickListener(dobtn3);
        terms.setOnClickListener(dobtn5);
        clear.setOnClickListener(dobtn6);
        insert.setOnClickListener(dobtn2);

    }

    private final Button.OnClickListener dobtn2 = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            account_numberString = account_number.getText().toString().trim();
            nameString = name.getText().toString().trim();
            phoneString = phone.getText().toString().trim();
            passwordString = password.getText().toString().trim();
            repasswordString = repassword.getText().toString().trim();
            realnameString = realname.getText().toString().trim();
            emailString = email.getText().toString().trim();
            number = account_number.getText().length();
            pass = password.getText().length();
            phonglength = phone.getText().length();
            if (account_numberString.matches("") || nameString.matches("") || passwordString.matches("") || repasswordString.matches("") || realnameString.matches("") || phoneString.matches("") || emailString.matches("")) { //空白
                if (toast != null) {
                    toast.cancel();
                } //讓訊息不要延遲
                Toasty.warning(Registered.this, "不能有空白", Toast.LENGTH_SHORT, true).show();


            } else {
                if (!check.isChecked()) {
                    if (toast != null) {
                        toast.cancel();
                    } //讓訊息不要延遲
                    Toasty.warning(Registered.this, "未勾選會員條款!", Toast.LENGTH_SHORT, true).show();

                } else {
                    if (number_message.getText().toString().matches("帳號可以使用!  ") && password_message.getText().toString().matches("可以使用!  ") && repassword_message.getText().toString().matches("正確!  ") && realname_message.getText().toString().matches("可以使用!  ") && phone_message.getText().toString().matches("可以使用!  ") && email_message.getText().toString().matches("可以使用!  ")) {
                        if (toast != null) {
                            toast.cancel();
                        } //讓訊息不要延遲
                        //密碼、信箱、電話、姓名加密
                        String passmd5 = MD5.MD5(passwordString);
                        String encryptPassMd5 = MD5.encryptmd5(passmd5);
                        String emailmd5 = MD5.MD5(emailString);
                        String encryptEmailMd5 = MD5.encryptmd5(emailmd5);
                        String phonemd5 = MD5.MD5(phoneString);
                        String encryptPhoneMd5 = MD5.encryptmd5(phonemd5);
                        String realnamemd5 = MD5.MD5(realnameString);
                        String encryptRealnameMd5 = MD5.encryptmd5(realnamemd5);
                        //
                        WriteRegisteredData.AddData(account_numberString, nameString, encryptPassMd5, encryptRealnameMd5, encryptPhoneMd5, encryptEmailMd5);
                        Toasty.success(Registered.this, "註冊成功!", Toast.LENGTH_SHORT, true).show();
                        finish();
                        //初始化Intent物件
                        Intent intent = new Intent();
                        //從registered 到MainActivity
                        intent.setClass(Registered.this, login.class);
                        //開啟Activity
                        startActivity(intent);
                    } else {
                        if (toast != null) {
                            toast.cancel();
                        } //讓訊息不要延遲
                        Toasty.warning(Registered.this, "輸入資料有誤，請再檢查一次!", Toast.LENGTH_SHORT, true).show();
                    }
                }
            }
        }
    };




    private final Button.OnClickListener dobtn3 = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            //初始化Intent物件
            Intent intent = new Intent();
            //從registered 到MainActivity
            intent.setClass(Registered.this, login.class);
            //開啟Activity
            startActivity(intent);

        }
    };
    private final Button.OnClickListener dobtn6 = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            account_number.setText("");
            name.setText("");
            realname.setText("");
            password.setText("");
            repassword.setText("");
            phone.setText("");
            email.setText("");
        }
    };




    private final Button.OnClickListener dobtn5 = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder terms = new AlertDialog.Builder(Registered.this);
            terms.setTitle("會員條款");
            terms.setMessage("歡迎您加入「運動58 APP平台」會員並使用 運動58 APP平台所提供之各項會員服務，運動58APP平台會員服務包含運動58APP所提供之會員服務，當您註冊完成或開始使用「會員服務」所提供之各項會員服務時，即視為您已經閱讀、瞭解並同意「會員服務」條款（以下簡稱「會員服務條款」），關於「會員服務條款」係規範您與「會員服務」所有人之契約。如果您不同意本約定書的內容，或者您所屬的國家或地域排除本約定書內容之全部或一部時，您應立即停止使用「會員服務」。\n" +
                    "\n" +
                    "如您是未滿二十歲之未成年人，則您必須在加入會員前將 「會員服務條款」請您的父母或監護人閱讀，並得到其同意，才可以註冊及使用「會員服務」。當您使用「會員服務」時，即視為您的父母或監護人已經閱讀、瞭解並同意 「會員服務條款」。\n" +
                    "\n" +
                    "您的註冊義務\n" +
                    "請依註冊申請程序所提示之項目，登錄您本人正確、真實及完整之個人資料。當您的個人資料有異動時，請立即更新，以維持您個人資料之正確、真實及完整。如因您登錄不實資料或冒用他人名義以致於侵害他人之權利或違法時，應自負法律責任。\n" +
                    "\n" +
                    "如您所提供個人資料不實或個人資料有異動但沒有更新以致於與原登錄之資料不符時，「會員服務」有權隨時終止您的會員資格及各項會員服務。\n" +
                    "\n" +
                    "請勿使用違反善良風俗或傷害會員之暱稱，若經認定不適當，本站將予以凍結帳號。\n" +
                    "\n" +
                    "會員服務\n" +
                    "於您註冊程序完成並由「會員服務」完成相關設定後，您即取得「會員服務」會員之資格，您可以開始使用「會員服務」。\n" +
                    "當您成為「會員服務」會員後而得使用之各項會員服務，其所有權仍屬「會員服務」及其所約定之人所有，會員僅得依「會員服務條款」之約定使用，不得出租、出借、移轉或讓與給其他第三人使用。\n" +
                    "\n" +
                    "「會員服務」有權增加、變更或取消會員服務中相關系統或功能之全部或一部之權利且無須個別通知會員；且有關現有或將來之各項服務均受「會員服務條款」之規範。\n" +
                    "「會員服務」隱私權保護政策\n" +
                    "您的註冊資料及其他相關資料，均受到隱私保護政策保護，請瞭解以保障您的權益。\n" +
                    "\n" +
                    "會員帳號及密碼之保管\n" +
                    "您註冊完成後將會取得一組帳號及密碼，您有義務妥善保管您的帳號及密碼之機密與安全，不得洩漏給任何第三人。您必須為此組帳號及密碼之一切行為負責，此組帳號及密碼所做之一切行為即視為您本身之行為。\n" +
                    "本組帳號及密碼僅限於您個人使用，您不得出租、出借、移轉或讓與給其他第三人使用。\n" +
                    "如您發現您的帳號及密碼遭盜用或其他不當使用，您必須立即通知「會員服務」，以利「會員服務」採取相關措施。但不得將「會員服務」所採取之措施解釋為「會員服務」對您有明示或默示擔保或賠償責任，對於您的帳號及密碼遭人非法使用，「會員服務」亦不負任何賠償責任。\n" +
                    "每次使用完畢後，應確實登出並結束您帳號及密碼的使用，以防止被他人盜用。\n" +
                    "兒童及青少年之保護\n" +
                    "因網路上之資訊眾多，難免有不適合於兒童及青少年之資訊存在，如暴力、色情或犯罪等資訊，為保護兒童及青少年受到不當資訊的影響，父母或監護人必須盡到以下之義務：\n" +
                    "\n" +
                    "必須於兒童或青少年使用「會員服務」時檢查其交友狀況及隱私權保護政策。\n" +
                    "對於十二歲以上未滿十八歲之青少年，應避免其接觸含有暴力、色情或鼓勵犯罪等資訊之訊息；對於未滿十二歲之兒童，則應全程陪同其上網，以避免兒童接觸到不當資訊。\n" +
                    "會員之義務\n" +
                    "會員同意接受「會員服務」會員中心寄發的會員電子報。若您不想要接收電子報，請盡速通知我們，將予以取消電子報之發送。\n" +
                    "除了遵守「會員服務條款」之約定外，您同意遵守「會員服務」之各項服務營業規章及網際網路國際使用慣例與禮節之相關規定，並同意不從事以下行為：\n" +
                    "有竊取、更改、破壞他人資訊情事者。\n" +
                    "有擅自複製他人資訊轉售、轉載情事者。\n" +
                    "未經對方同意，擅自寄發電子廣告信件至對方信箱者。\n" +
                    "於討論區或回應區張貼與主題無關之訊息者。\n" +
                    "蓄意破壞他人信箱或其通信設備者。\n" +
                    "散播電腦病毒者。\n" +
                    "所為言論違背公序良俗者。\n" +
                    "擷取非經所有者正式開放或授權之資源者。\n" +
                    "侵害他人智慧財產權或其他權利或有侵害之虞者。\n" +
                    "未經同意收集他人電子郵件位址及其他個人資料者。\n" +
                    "使用任何不雅或有違善良風俗之圖像、文字、聲音、影像作為發表內容者。\n" +
                    "傳送、張貼或發表或任何虛偽不實或引人錯誤之廣告或其他表示或表徵者。\n" +
                    "其他有危害通信或違反法令之情事或之虞者。\n" +
                    "未經本站同意，於本站進行商業置入者。\n" +
                    "上述規定不代表「會員服務」對會員傳送、張貼或發表之內容做任何形式或實質之審查，會員必須對自己所做之行為負責。如經「會員服務」察覺或經他人申訴會員有違反上述各款之情事或之虞時，「會員服務」除有權逕行移除或刪除該內容外並有權終止或暫停該會員之會員資格及各項會員服務。「會員服務」如因此產生損害或損失，並得向該會員請求賠償。\n" +
                    "服務暫停或中斷\n" +
                    "「會員服務」於下列情形之一時，得暫停或中斷「會員服務」之全部或一部，對於使用者不負擔任何賠償責任：\n" +
                    "對於「會員服務」相關系統設備進行遷移、更換或維護時。\n" +
                    "因不可歸責於「會員服務」所造成服務停止或中斷。\n" +
                    "因不可抗力所造成服務停止或中斷。\n" +
                    "如因「會員服務」對於「會員服務」相關系統設備進行遷移、更換或維護而必須暫停或中斷全部或一部之服務時，「會員服務」於暫停或中斷前將於官方部落格或「會員服務」上公告。\n" +
                    "因使用者違反法令或「會員服務條款」或因不可歸責於「會員服務」之事由而造成「會員服務」之全部或一部暫停或中斷時，暫停或中斷期間之費用仍依正常標準計費。\n" +
                    "（四）對於「會員服務」之暫停或中斷，可能造成您使用上的不便、資料遺失或其他經濟及時間上之損失，您平時應採取適當的防護措施，以保障您的權益。\n" +
                    "網路資源之使用\n" +
                    "網際網路係開放性網路，並由各網路提供者負責維護，「會員服務」無法就使用者擷取資訊資源之正確性、完整性、安全性、可靠性、合適性、不會斷線及出錯負責，亦不保證使用者與「會員服務」間資料傳輸速率，倘客戶因傳輸速率或擷取使用網路資料而導致直接或間接之損害或損失時，「會員服務」不負任何損害賠償責任。\n" +
                    "「會員服務」網路上可擷取之任何資源，皆屬該資料所有者所擁有，使用者應遵守各該網路資訊所有者之授權或其他法律規定，否則不得使用該等資源，如因此導致任何責任皆由使用者自行負責，概與「會員服務」無涉。\n" +
                    "會員服務之措施及限制\n" +
                    "您同意「會員服務」有權就「會員服務」訂定相關的措施及限制。\n" +
                    "如「會員服務」將會員傳送之任何訊息及內容刪除或未儲存，「會員服務」不負擔任何損害賠償責任，您應自行定期備份資料。\n" +
                    "「會員服務」得因業務考量，隨時變更該措施及限制，對於前述所做之變更，將不對會員個別通知或公告。\n" +
                    "資訊或建議\n" +
                    "「會員服務」所出現的資訊可能因為「會員服務」、其他協力廠商及相關電信業者網路系統軟硬體設備之故障或失靈而全部或一部暫時無法使用、或造成資料傳輸錯誤、或遭第三人侵入系統竄改或偽造變造資料等；因此使用者在閱讀或使用所有出現在「會員服務」上的資訊時，包括各種資料、建議、分析、評論和報導等，都應該特別謹慎，該等資訊既不代表「會員服務」的言論或意見，「會員服務」也無法擔保其真實性、完整性及可信度，「會員服務」所有資訊和連結，都只是為了提供給使用者自行研究參考，並非對使用者作任何有關專業建議或暗示，亦無法取代或補充專業顧問之個別分析建議，使用者在作成任何具體決定時，應該自行謹慎作成判斷，或者另外洽詢專業顧問。使用者基於「會員服務」上的任何資訊所作成的任何決定，都必須自行負擔風險，「會員服務」不負任何責任。\n" +
                    "\n" +
                    "與第三人網站之連結\n" +
                    "「會員服務」與其他網站之連結，僅在提供使用者方便且迅速連結的資訊，您透過交換連結所得的資料由第三人網站所提供，不代表「會員服務」與連結網站之業者有任何合作關係。如您因到達該網站而產生之糾紛或損害，請您逕行與該網站之業者聯絡，「會員服務」不負任何責任。\n" +
                    "\n" +
                    "廣告\n" +
                    "「會員服務」所傳播或刊載之廣告，關於其內容所展現的文字、圖片、動畫、聲音、影像或以其他方式對商品或服務之說明，都是由廣告主或廣告代理商所提供，對於廣告之內容，「會員服務」基於尊重廣告主或廣告代理商權利，不對廣告之內容進行審查。您應該自行判斷該廣告的正確性及可信度，「會員服務」不對該廣告做任何擔保。\n" +
                    "\n" +
                    "商品或服務之交易\n" +
                    "如果您透過「會員服務」與網路上其他會員或業者進行商品或服務之買賣或各種交易行為，此時僅該會員或業者與您有契約關係存在，「會員服務」並不介入或參與。如有該商品或服務有任何瑕疵或糾紛時，請您逕行與該會員或業者聯絡解決，「會員服務」對此不負任何擔保責任。如果商品或服務是「會員服務」所提供，則相關權利義務依各會員服務所訂定之相關規定辦理。\n" +
                    "\n" +
                    "內容提供者\n" +
                    "「會員服務」之內容如由與他「會員服務」有合作關係之公司、業者或廠商所提供，「會員服務」基於尊重他人智慧財產權及其他權益，不對其所提供之內容做任何實質的審查，使用者對於內容之正確性及可靠性，應審慎判斷，「會員服務」不對此作任何的擔保。該內容如有不實或錯誤，您應該逕行向該內容提供者反映。\n" +
                    "\n" +
                    "擔保責任之免除\n" +
                    "「會員服務」之各項會員服務，依「會員服務」既有之規劃提供，對於特定使用者之特殊需求，「會員服務」不擔保「會員服務」將符合您的所有需求。\n" +
                    "「會員服務」不擔保各項服務之正確、完整、安全、可靠、合適、時效、穩定、不會斷線及出錯。您同意對於您所傳送之檔案及其他資料做備份。關於因傳送過程而造成通訊錄、檔案及其他資料之遺失，「會員服務」不負任何擔保責任。\n" +
                    "對於您於「會員服務」中所下載之檔案及其他資料，您應該自行考量其風險，如因下載而造成您電腦系統的損壞或電腦中資料的遺失，「會員服務」不負任何擔保責任。\n" +
                    "會員內容之保留或揭露\n" +
                    "「會員服務」有權對於會員所傳送、張貼或發表之內容（包括但不限於文字、圖片、音樂、影像、軟體、資訊及各種資料等）進行審查，確保其內容不會違反善良風俗或造成侵權行為。然而，會員對於使用由其他會員所提供之內容應自行考量其風險。\n" +
                    "「會員服務」基於下列理由會將會員所傳送、張貼或發表之內容保留或揭露：\n" +
                    "司法或警察機關偵查犯罪需要時。\n" +
                    "政府機關依法律程序要求時。\n" +
                    "因會員之行為違反法令或「會員服務條款」之規定時。\n" +
                    "基於維護公益或保護「會員服務」或他人權利。\n" +
                    "購買規範\n" +
                    "所有在會員服務網站所進行的線上消費，各項商品之所有權皆屬各該商品或服務的提供者，唯其交易行為及付款機制等係存在於會員服務網站與使用者之間，會員服務網站保留是否接受訂單之權利。\n" +
                    "相關商品或服務之品質、保固及售後服務，由提供各該商品或服務的提供者負責，但會員服務網站將會全力協助使用者解決關於因為線上消費所產生之疑問或爭議。\n" +
                    "使用者資料（如地址、電話）如有變更時，應立即上線修正其所留存之資料，或通知會員服務網站修正，且不得以資料不符為理由，否認其訂購行為或拒絕付款。\n" +
                    "所有在會員服務網站所進行的線上消費，使用者應同意以會員服務網站之交易紀錄為準，如有糾紛，並以該交易紀錄為認定標準。使用者如果發現交易紀錄不正確，應立即通知會員服務網站。\n" +
                    "當使用者訂單成立之後，為確保為本人授權之交易，會員服務網站得保留向持卡人索取信用卡背面簽名傳真與訂單確認傳真之權利。\n" +
                    "您瞭解所有「58幣」，一經購買後，即表示您已經確認會員服務所處理之結果，本公司無法進行回復、退費或轉讓等動作，而且「58幣」之權利義務適用該本公司所有相關規定。同時，若是因為您的保管疏忽，而導致帳號、密碼遭他人非法使用時，本公司將不負責處理。若您於會員服務中與他人為任何交易或互易行為，因此而產生的糾紛，本公司恕不負責，本公司亦無法進行回覆或退費等動作，但會員服務可提供相關交易紀錄以供查詢。若本公司無法收到您經由線上刷卡或其他不是使用現金方式購買「58幣」或其他商品的費用時，您同意本公司有權停止與該項交易相關之帳號。\n" +
                    "會員服務之「58幣」，交易僅提供會員於會員服務平台上使用，任何會員不得於他途上私自販售，如私做實體點數卡，否則將有侵權行為，本公司得要求所有相關之損失及費用，其包括但不限於違約金、訴訟費、律師費、及和解金等相關費用\n" +
                    "所有在會員服務網站所進行的線上消費，如有消費爭議時，使用者同意將個人相關資料提供給第三方金流廠商，進行相關消費了解與查詢。\n" +
                    "補償\n" +
                    "您同意補償本公司及一切相關人員，因您違反相關法令或本規範所致生之一切損害及責任。\n" +
                    "\n" +
                    "風險\n" +
                    "請本諸謹慎、合於常理判斷、交易安全性 ( 包括但不限於對方交易者行為能力、身份真實性、與法定年齡以下之人交易、國際貿易等風險 ) 等的考量使用會員服務。\n" +
                    "\n" +
                    "智慧財產權\n" +
                    "「會員服務」上所使用或提供之軟體、程式及內容（包括但不限於文字、說明、圖畫、圖片、圖形、檔案、頁面設計、網站規劃與安排等）之專利權、著作權、商標權、營業秘密、專門技術及其他智慧財產權均屬「會員服務」或其他權利人所有，非經權利人事先書面授權同意，會員不得重製、公開傳播、公開播送、公開上映、改作、編輯、出租、散佈、進行還原工程、解編、反向組譯、或其他方式之使用，如有違反，除應自行負法律責任外，如因而對「會員服務」造成損害或損失，「會員服務」得向該會員請求損害賠償。\n" +
                    "\n" +
                    "特別規定\n" +
                    "「會員服務條款」係就「會員服務」之各項服務所作之一般規定，如各該會員服務有特別規定時，則先依各該特別規定。\n" +
                    "\n" +
                    "服務條款之增訂及修改\n" +
                    "「會員服務條款」如有增訂或修改，您同意自該修訂條款於「會員服務」公告之時起受其拘束，「會員服務」將不對會員個別通知。如您於公告後繼續使用「會員服務」，則視為您已經\n" +
                    "同意該修訂條款。\n" +
                    "\n" +
                    "使用玩運彩玩家註冊，請由本人使用自己在玩運彩的帳號操作這項服務，當按下玩運彩玩家註冊後，確認為本人帳號後，按下OK，代表你同意您在玩運彩的榮譽，帳號，移轉至運動58。\n" +
                    "\n" +
                    "運動58賽事預測\n" +
                    "會員同意授權「會員服務」，得為提供服務及贈獎之目的，提供必需之會員資料給合作夥伴(第三者)做約定範圍內之妥善使用，倘若會員不同意將其資料列 於合作夥伴(第三者)產品或服務名單內，可以通知「會員服務」，於名單中刪除其資料，同時放棄其「會員服務」購物以外之優惠或獲獎權利。\n" +
                    "對於會員所登錄或留存之個人資料，會員同意「會員服務」得於合理之範圍內蒐集、處理、保存、傳遞及使用該等資料，以提供使用者其他資訊或服務、或作成會員統計資料、或進行關於網路行為之調查或研究，或為任何之合法使用。\n" +
                    "若會員無合法權利得授權他人使用、修改、重製、公開播送、改作、散佈、發行、公開發表某資料，並將前述權利轉授權第三人，請勿擅自將該資料上載、傳送、輸入或提供至「會員服務」。任何資料一經您上載、傳送、輸入或提供至「會員服務」時，視為您已允許「會員服務」無條件使用、修改、重 製、公開播送、改作、散佈、發行、公開發表該等資料，並得將前述權利轉授權他人，您對此絕無異議。您並應保證「會員服務」使用、修改、重製、公開 播送、改作、散佈、發行、公開發表、轉授權該等資料，不致侵害任何第三人之智慧財產權，否則應對「會員服務」負損害賠償責任（包括但不限於訴訟費 用及律師費用等）。\n" +
                    "準據法及管轄法院\n" +
                    "關於「會員服務條款」之解釋或適用，以中華民國之法律為準據法。\n" +
                    "會員因使用「會員服務」而與「會員服務」所生之爭議，同意本誠信原則解決之，如有訴訟之必要時，同意以台灣台北地方法院為第一審管轄法院。");
            terms.show();
        }
    };

    private final TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            account_numberString = account_number.getText().toString().trim();
            nameString = name.getText().toString().trim();
            phoneString = phone.getText().toString().trim();
            passwordString = password.getText().toString().trim();
            repasswordString = repassword.getText().toString().trim();
            realnameString = realname.getText().toString().trim();
            emailString = email.getText().toString().trim();
            number = account_number.getText().length();
            pass = password.getText().length();
            phonglength = phone.getText().length();
            if (!account_number.getText().toString().matches("")) {
                if (number < 6) {
                    number_message.setText("");
                    number_message.setTextColor(Color.parseColor("#ffff4444")); //設置字體紅色
                    number_message.setText("帳號必須六位以上");
                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("member");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            int count = 0;
                            for (DataSnapshot ds: snapshot.getChildren()) {
                                String Account = ds.child("account").getValue().toString();
                                if (Account.equals(account_numberString)) {
                                    //存在
                                    number_message.setText("");
                                    number_message.setTextColor(Color.parseColor("#ffff4444")); //設置字體紅色
                                    number_message.setText("帳號已存在!");
                                    break;
                                } else {
                                    count++;
                                    if (count == snapshot.getChildrenCount()) {
                                        //沒被使用過
                                        number_message.setText("");
                                        number_message.setTextColor(Color.parseColor("#ff99cc00")); //設置字綠色
                                        number_message.setText("帳號可以使用!  ");
                                        account_number.clearFocus();
                                        break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                }
            }
            else{
                number_message.setText("");
            }



            if (!passwordString.matches("")) {
                if (pass < 6) {
                    password_message.setText("");
                    password_message.setTextColor(Color.parseColor("#ffff4444")); //設置字體紅色
                    password_message.setText("密碼必須六位以上  ");
                } else {
                    password_message.setText("");
                    password_message.setTextColor(Color.parseColor("#ff99cc00")); //設置字綠色
                    password_message.setText("可以使用!  ");
                }
            }
            else{
                password_message.setText("");
            }

            if (!repasswordString.matches("")) {
                if (!passwordString.equals(repasswordString)) {
                    repassword_message.setText("");
                    repassword_message.setTextColor(Color.parseColor("#ffff4444")); //設置字體紅色
                    repassword_message.setText("兩次密碼不一致!  ");
                } else {
                    repassword_message.setText("");
                    repassword_message.setTextColor(Color.parseColor("#ff99cc00")); //設置字綠色
                    repassword_message.setText("正確!  ");
                }
            }
            else{
                repassword_message.setText("");
            }
            if (!realnameString.matches("")) {
                if (!checkChinese.isChineseChar(realnameString)) {
                    realname_message.setText("");
                    realname_message.setTextColor(Color.parseColor("#ffff4444")); //設置字體紅色
                    realname_message.setText("只能輸入中文姓名!  ");
                } else {
                    realname_message.setText("");
                    realname_message.setTextColor(Color.parseColor("#ff99cc00")); //設置字綠色
                    realname_message.setText("可以使用!  ");
                }
            }
            else{
                realname_message.setText("");
            }
            if (!phoneString.matches("")) {
                if (phonglength != 10 || !checkPhone.isPhone(phoneString)) {
                    phone_message.setText("");
                    phone_message.setTextColor(Color.parseColor("#ffff4444")); //設置字體紅色
                    phone_message.setText("手機格式錯誤!  ");
                } else {
                    phone_message.setText("");
                    phone_message.setTextColor(Color.parseColor("#ff99cc00")); //設置字綠色
                    phone_message.setText("可以使用!  ");
                }
            }
            else{
                phone_message.setText("");
            }
            if (!emailString.matches("")) {
                if (!checkEmail.isEmailValid(emailString)) {
                    email_message.setText("");
                    email_message.setTextColor(Color.parseColor("#ffff4444")); //設置字體紅色
                    email_message.setText("信箱格式錯誤!");
                } else {
                    email_message.setText("");
                    email_message.setTextColor(Color.parseColor("#ff99cc00")); //設置字綠色
                    email_message.setText("可以使用!  ");
                }
            }
            else{
                email_message.setText("");
            }
        }
    };










}