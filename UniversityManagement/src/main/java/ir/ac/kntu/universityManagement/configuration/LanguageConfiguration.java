package ir.ac.kntu.universityManagement.configuration;


import ir.ac.kntu.universityManagement.controllers.general.BaseController;
import java.util.ArrayList;

public class LanguageConfiguration {

    private final ArrayList<String> fxmlID;
    private final ArrayList<String> text;
    private final String viewName;

    public LanguageConfiguration(ArrayList<String> fxmlID,ArrayList<String> text,String viewName){
        this.fxmlID = fxmlID;
        this.text = text;
        this.viewName = viewName;


        text.clear();


        switch (BaseController.getLanguage()){
            case "Persian":
                persianLanguageTranslator();
            break;
            case "English":
                englishLanguageTranslator();
            break;
        }
    }


    //---------------------------------------------------------Persian language
    private void persianLanguageTranslator(){

        switch (viewName){

            case "About":
                aboutPersianTranslate();
            break;

            case "CourseManagement":
                courseManagementPersianTranslate();
            break;

            case "InstructorManagement":
                instructorManagementPersianTranslate();
            break;

            case "StudentManagement":
                studentManagementPersianTranslate();
                break;

            case "FacultyManagement":
                facultyManagementPersianTranslate();
                break;

            case "UserManagement":
                userManagementPersianTranslate();
                break;

            case "ViewPersonalInfo":
                viewPersonalInfoPersianTranslate();
                break;
        }
    }

    private void aboutPersianTranslate(){
        for(String string : fxmlID){

            switch (string){

                case "aboutUsHeader":
                    text.add("درباره ما");
                    break;

                case "homePageButton":
                    text.add("خانه");
                    break;

                case "aboutUsButton":
                    text.add("درباره ما");
                    break;

                case "aboutUsAppName":
                    text.add("مدیریت دانشگاه ورژن 1.2");
                    break;

                case "aboutAppHeader":
                    text.add("با استفاده از این برنامه میتوانید:");
                    break;

                case "aboutTextArea":
                    String textArea =   "1-مدیران را مدیریت کنید:\n مدیر اضافه کنید،نقش مدیر را تعیین کنید،مدیر حذف کنید.\n\n"  +
                                        "2-دروس را مدیریت کنید:\n به عنوان یک دانشجو یا یک استاد دورس ترم جاری خود را مشاهده کنید،\nبه عنوان ادمین درس جدید اضافه کنید،درسی را حذف کنید و درسی را تغییر بدهید.\n\n" +
                                        "3-دانشجویان را مدیریت کنید:\n دانشجو اضافه کنید،دانشجویی را حذف کنید،دانشجویی را تغییر بدهید \nو لیستی از دانشجویان دانشگاه ببینید.\n\n" +
                                        "4-استادان را مدیریت کنید:\n استاد اضافه کنید،استادی را حذف کنید،استادی را تغییر بدهید \nو لیستی از استادان دانشگاه ببینید.\n\n" +
                                        "5-دانشکده ها را مدیریت کنید:\n دانشکده اضافه کنید،دانشکده ایی را حذف کنید یا تغییر بدهید \nو لیستی از دانشکده های دانشگاه ببینید.\n\n";

                    text.add(textArea);
                    break;

                case "aboutUsEmail":
                    text.add("ایمیل: adelmirsharji87@gmail.com");
                    break;

                case "aboutUsPhoneNumber":
                    text.add("شماره تماس: 09351105338");

            }
        }
    }

    private void courseManagementPersianTranslate(){
        for(String string : fxmlID){

            switch (string){

                case "homePageButton":
                    text.add("خانه");
                    break;

                case "addCourseButton":
                    text.add("اضافه کردن درس");
                    break;

                case "editCourseButton":
                    text.add("تغییر دادن درس");
                    break;

                case "deleteCourseButton":
                    text.add("پاک کردن درس");
                    break;

                case "ViewAllCoursesButton":
                    text.add("لیست دروس");
                    break;

                case "refreshTablesButton":
                    text.add("بازیابی جداول");
                    break;

                case "courseSearchBy":
                    text.add("جستجو بر اساس");
                    break;

                case "instructorTableSearchBy":
                    text.add("جستجو بر اساس");
                    break;

                case "prerequisitesSearchBy":
                    text.add("جستجو بر اساس");
                    break;

                case "corequisitesSearchBy":
                    text.add("جستجو بر اساس");
                    break;

                case "courseId":
                    text.add("آی دی درس");
                    break;

                case "courseName":
                    text.add("نام درس");
                    break;

                case "courseCapacity":
                    text.add("ظرفیت درس ");
                    break;

                case "courseInstructor":
                    text.add("استاد درس");
                    break;

                case "courseSemester":
                    text.add("ترم درس");
                    break;

                case "courseStartDate":
                    text.add("زمان شروع درس");
                    break;

                case "courseFinalExam":
                    text.add("زمان امتحان نهایی درس");
                    break;

                case "courseUnits":
                    text.add("تعداد واحد درس");
                    break;

                case "courseSchedules":
                    text.add("زمانبندی درس");
                    break;

                case "courseFaculty":
                    text.add("دانشکده درس");
                    break;

                case "instructorId":
                    text.add("آی دی استاد");
                    break;

                case "instructorFirstName":
                    text.add("نام کوچک استاد");
                    break;

                case "instructorLastName":
                    text.add("نام خانوادگی استاد");
                    break;

                case "prerequisitesCourseId":
                    text.add("آی دی درس");
                    break;

                case "prerequisitesCourseName":
                    text.add("نام درس");
                    break;

                case "corequisitesCourseId":
                    text.add("آی دی درس");
                    break;

                case "corequisitesCourseName":
                    text.add("نام درس");
                    break;

                case "addCourseHeaderLabel":
                    text.add("اضافه کردن درس جدید");
                    break;

                case "editCourseHeaderLabel":
                    text.add("تغییر دادن درس");
                    break;

                case "addNameLabel":
                    text.add("نام درس");
                    break;

                case "addCapacityLabel":
                    text.add("ظرفیت درس");
                    break;

                case "addInstructorIdLabel":
                    text.add("آی دی استاد درس");
                    break;

                case "addUnitsLabel":
                    text.add("تعداد واحد درس");
                    break;

                case "addScheduleLabel":
                    text.add("زمانبندی درس");
                    break;

                case "addDayOneLabel":
                    text.add("روز اول");
                    break;

                case "addDayTwoLabel":
                    text.add("روز دوم");
                    break;

                case "addTimeIntervalLabel":
                    text.add("فاصله زمانی");
                    break;

                case "addFacultyLabel":
                    text.add("دانشکده درس");
                    break;

                case "addStartDateLabel":
                    text.add("زمان شروع درس");
                    break;

                case "addFinalExamLabel":
                    text.add("زمان پایانترم درس");
                    break;

                case "addSemesterLabel":
                    text.add("ترم درس");
                    break;

                case "save":
                    text.add("ذخیره");
                    break;

                case "addToPrerequisites":
                    text.add("اضافه به پیش نیاز درس");
                    break;

                case "addToCorequisites":
                    text.add("اضافه به هم نیاز درس");
                    break;

                case "deletePrerequisites":
                    text.add("حذف پیش نیاز");
                    break;

                case "deleteCorequisites":
                    text.add("حذف هم نیاز");
                    break;

                case "editCourseIdLabel":
                    text.add("آی دی درس");
                    break;

                case "enableChoose2Edit":
                    text.add("فعال کردن انتخاب برای تغییر");
                    break;

                case "youSureLabel":
                    text.add("آیا مطمئن هستید؟");
                    break;

                case "yes":
                    text.add("بله");
                    break;

                case "no":
                    text.add("خیر");
                    break;

                case "delete":
                    text.add("حذف");
                    break;

                case "prerequisitesLabel":
                    text.add("هم نیاز");
                    break;

                case "corequisitesLabel":
                    text.add("پیش نیاز");
                    break;

                case "courseManagementHeader":
                    text.add("مدیریت درس");
                    break;

                case "universityManagementHeader":
                    text.add("مدیریت\nدانشگاه");
                    break;

            }
        }
    }

    private void instructorManagementPersianTranslate(){
        for(String string : fxmlID){

            switch (string){
                case "goBackToHomePage":
                    text.add("خانه");
                    break;

                case "goToInstructorManagement":
                    text.add("اضافه کردن استاد");
                    break;

                case "goToEditInstructor":
                    text.add("تغییر دادن استاد");
                    break;

                case "goToDeleteInstructor":
                    text.add("حذف کردن استاد");
                    break;

                case "gotoViewAllInstructors":
                    text.add("لیست اساتید");
                    break;

                case "refreshTablesButton":
                    text.add("بازیابی جداول");
                    break;

                case "instructorId":
                    text.add("آی دی استاد");
                    break;

                case "instructorFirstName":
                    text.add("نام کوچک استاد");
                    break;

                case "instructorLastName":
                    text.add("نام خانوادگی استاد");
                    break;

                case "instructorPhoneNumber":
                    text.add("شماره تماس استاد");
                    break;

                case "instructorEmail":
                    text.add("ایمیل استاد");
                    break;

                case "instructorAddress":
                    text.add("آدرس استاد");
                    break;

                case "instructorFaculty":
                    text.add("دانشکده استاد");
                    break;

                case "instructorNationalId":
                    text.add("کد ملی استاد");
                    break;

                case "instructorUserInfo":
                    text.add("یوسرنیم استاد");
                    break;

                case "instructorBirthdate":
                    text.add("تاریخ تولد استاد");
                    break;

                case "instructorEntranceDate":
                    text.add("سال ورود استاد");
                    break;

                case "instructorSearchBy":
                    text.add("جسجتو بر اساس");
                    break;

                case "userSearchBy":
                    text.add("جسجتو بر اساس");
                    break;

                case "userUsername":
                    text.add("یوسرنیم کاربر");
                    break;

                case "userRole":
                    text.add("نقش کاربر");
                    break;

                case "facultySearchBy":
                    text.add("جسجتو بر اساس");
                    break;

                case "facultyName":
                    text.add("نام دانشکده");
                    break;

                case "addInstructorHeader":
                    text.add("اضافه کردن استاد");
                    break;

                case "addNationalIdLabel":
                    text.add("کد ملی");
                    break;

                case "addFirstNameLabel":
                    text.add("نام کوچک");
                    break;

                case "addPhoneNumberLabel":
                    text.add("شماره تماس");
                    break;

                case "addEmailLabel":
                    text.add("ایمیل");
                    break;

                case "addLastNameLabel":
                    text.add("نام خانوادگی");
                    break;

                case "addBirthdateLabel":
                    text.add("تاریخ تولد");
                    break;

                case "addAddressLabel":
                    text.add("آدرس");
                    break;

                case "addFacultyNameLabel":
                    text.add("نام دانشکده");
                    break;

                case "addUsernameLabel":
                    text.add("یوسرنیم");
                    break;

                case "addEntranceDateLabel":
                    text.add("سال ورود");
                    break;

                case "save":
                    text.add("ذخیره");
                    break;

                case "instructorManagementHeader":
                    text.add("مدیریت اساتید");
                    break;

                case "editInstructorHeader":
                    text.add("تغییر استاد");
                    break;

                case "editInstructorIdLabel":
                    text.add("آی دی");
                    break;

                case "youSure":
                    text.add("آیا مطمئن هستید؟");
                    break;

                case "yes":
                    text.add("بله");
                    break;

                case "no":
                    text.add("خیر");
                    break;

                case "delete":
                    text.add("حذف");
                    break;

                case "showFreeTimes":
                    text.add("نمایش زمان های \nخالی استاد انتخاب شده");
                    break;

                case "universityManagementHeader":
                    text.add("مدیریت\nدانشگاه");
                    break;

            }
        }
    }

    private void studentManagementPersianTranslate(){
        for (String string : fxmlID){
            switch (string) {

                case "goBackToHomePage":
                    text.add("خانه");
                    break;

                case "goToStudentManagement":
                    text.add("اضافه کردن دانشجو");
                    break;

                case "goToEditStudent":
                    text.add("تغییر دادن دانشجو");
                    break;

                case "goToDeleteStudent":
                    text.add("حذف دانشجو");
                    break;

                case "gotoViewAllStudent":
                    text.add("لیست دانشجویان");
                    break;

                case "refreshTablesButton":
                    text.add("بازیابی جداول");
                    break;

                case "studentManagementHeader":
                    text.add("مدیریت دانشجو");
                    break;

                case "studentNumber":
                    text.add("شماره دانشجویی");
                    break;

                case "studentFirstName":
                    text.add("نام کوچک دانشجو");
                    break;

                case "studentLastName":
                    text.add("نام خانوادگی دانشجو");
                    break;

                case "studentNationalId":
                    text.add("کد ملی دانشجو");
                    break;

                case "studentBirthdate":
                    text.add("تاریخ تولد دانشجو");
                    break;

                case "studentEmail":
                    text.add("ایمیل دانشجو");
                    break;

                case "studentPhoneNumber":
                    text.add("شماره تماس دانشجو");
                    break;

                case "studentAddress":
                    text.add("آدرس دانشجو");
                    break;

                case "studentUserInfo":
                    text.add("یوسرنیم دانشجو");
                    break;

                case "studentEntranceDate":
                    text.add("تاریخ ورود دانشجو");
                    break;

                case "studentFaculty":
                    text.add("دانشکده دانشجو");
                    break;

                case "studentSearchBy":
                    text.add("جستجو بر اساس");
                    break;

                case "userSearchBy":
                    text.add("جستجو بر اساس");
                    break;

                case "userUsername":
                    text.add("یوسرنیم");
                    break;

                case "userRole":
                    text.add("نقش");
                    break;

                case "facultySearchBy":
                    text.add("جستجو بر اساس");
                    break;

                case "facultyName":
                    text.add("نام دانشکده");
                    break;

                case "addFacultyName":
                    text.add("نام دانشکده");
                    break;

                case "addNationalId":
                    text.add("کد ملی");
                    break;

                case "addFirstName":
                    text.add("نام کوچک");
                    break;

                case "addLastName":
                    text.add("نام خانوادگی");
                    break;

                case "addBirthdate":
                    text.add("تاریخ تولد");
                    break;

                case "addEmail":
                    text.add("ایمیل");
                    break;

                case "addPhoneNumber":
                    text.add("شماره تماس");
                    break;

                case "addAddress":
                    text.add("آدرس");
                    break;

                case "addUsername":
                    text.add("یوسرنیم");
                    break;

                case "addEntranceDate":
                    text.add("سال ورود");
                    break;

                case "save":
                    text.add("ذخیره");
                    break;

                case "addStudentHeader":
                    text.add("اضافه کردن دانشجو");
                    break;

                case "editStudentHeader":
                    text.add("تغییر دانشجو");
                    break;

                case "editStudentID":
                    text.add("شماره دانشجویی");
                    break;

                case "youSure":
                    text.add("آیا مطمئن هستید؟");
                    break;

                case "delete":
                    text.add("حذف");
                    break;

                case "yes":
                    text.add("بله");
                    break;

                case "no":
                    text.add("خیر");
                    break;

                case "universityManagementHeader":
                    text.add("مدیریت\nدانشگاه");
                    break;
            }
        }
    }

    private void facultyManagementPersianTranslate(){
        for(String string : fxmlID){

            switch (string){

                case "homePageButton":
                    text.add("خانه");
                    break;

                case "addFacultyButton":
                    text.add("اضافه کردن دانشکده");
                    break;

                case "editFacultyButton":
                    text.add("تغییر دانشکده");
                    break;

                case "deleteFacultyButton":
                    text.add("حذف دانشکده");
                    break;

                case "ViewAllFacultiesButton":
                    text.add("مشاهده لیست دانشکده ها");
                    break;

                case "refreshTablesButton":
                    text.add("بازیابی جداول");
                    break;

                case "facultyManagementHeader":
                    text.add("مدیریت دانشکده");
                    break;

                case "facultyId":
                    text.add("آی دی دانشکده");
                    break;

                case "facultyName":
                    text.add("نام دانشکده");
                    break;

                case "facultyNumberOfClasses":
                    text.add("تعداد کلاسهای دانشکده");
                    break;

                case "facultyNumberOfStudents":
                    text.add("تعداد دانشجوهای دانشکده");
                    break;

                case "facultyNumberOfInstructors":
                    text.add("تعداد اساتید دانشکده");
                    break;

                case "facultyPhoneNumber":
                    text.add("شماره تماس دانشکده");
                    break;

                case "facultySearchBy":
                    text.add("جسجتو بر اساس");
                    break;

                case "addFacultyNameLabel":
                    text.add("نام دانشکده");
                    break;

                case "addFacultyNumberOfClassesLabel":
                    text.add("تعداد کلاسها");
                    break;

                case "addFacultyNumberOfStudentsLabel":
                    text.add("تعداد دانشجوها");
                    break;

                case "addFacultyNumberOfInstructorsLabel":
                    text.add("تعداد اساتید");
                    break;

                case "addFacultyPhoneNumberLabel":
                    text.add("شماره تماس");
                    break;

                case "addFacultyHeader":
                    text.add("اضافه کردن دانشجو");
                    break;

                case "editFacultyHeader":
                    text.add("تغییر دادن دانشجو");
                    break;

                case "save":
                    text.add("ذخیره");
                    break;

                case "editFacultyIDLabel":
                    text.add("آی دی");
                    break;

                case "youSure":
                    text.add("آیا مطمئن هستید؟");
                    break;

                case "no":
                    text.add("خیر");
                    break;

                case "yes":
                    text.add("بله");
                    break;

                case "delete":
                    text.add("حذف");
                    break;

                case "universityManagementHeader":
                    text.add("مدیریت\nدانشگاه");
                    break;

            }
        }
    }

    private void userManagementPersianTranslate(){
        for(String string : fxmlID){

            switch (string){

                case "goBackToHomePage":
                    text.add("خانه");
                    break;

                case "goToUserManagement":
                    text.add("اضافه کردن کاربر");
                    break;

                case "goToEditUser":
                    text.add("تغییر دادن کاربر");
                    break;

                case "goToDeleteUser":
                    text.add("حذف کردن کاربر");
                    break;

                case "gotoViewAllUser":
                    text.add("مشاهده لیست کاربران");
                    break;

                case "refreshTablesButton":
                    text.add("بازیابی جداول");
                    break;

                case "userManagementHeader":
                    text.add("مدیریت کاربر");
                    break;

                case "username":
                    text.add("یوسرنیم");
                    break;

                case "userRole":
                    text.add("نقش");
                    break;

                case "userSearchBy":
                    text.add("جسجتو بر اساس");
                    break;

                case "addUserHeaderLabel":
                    text.add("اضافه کردن کاربر");
                    break;

                case "addUserRoleLabel":
                    text.add("نقش");
                    break;

                case "addRepeatPasswordLabel":
                    text.add("تکرار رمز");
                    break;

                case "addUserNameLabel":
                    text.add("نام کاربر");
                    break;

                case "addPasswordLabel":
                    text.add("رمز");
                    break;

                case "save":
                    text.add("ذخیره");
                    break;

                case "delete":
                    text.add("حذف");
                    break;

                case "youSure":
                    text.add("آیا مطمئن هستید؟");
                    break;

                case "yes":
                    text.add("بله");
                    break;

                case "no":
                    text.add("خیر");
                    break;

                case "universityManagementHeader":
                    text.add("مدیریت\nدانشگاه");
                    break;
            }
        }
    }

    private void viewPersonalInfoPersianTranslate(){
        for(String string : fxmlID){

            switch (string) {

                case "homePageButton":
                    text.add("خانه");
                    break;

                case "selected":
                    text.add("مشخصات شخصی");
                    break;

                case "viewPersonalInfoButton":
                    text.add("مشخصات شخصی");
                    break;

                case "editPersonalInfo":
                    text.add(" تغییر مشخصات شخصی");
                    break;

                case "personalInfoHeader":
                    text.add("مشخصات شخصی");
                    break;

                case "logOut":
                    text.add("خروج از حساب کاربری");
                    break;

                case "userNameLabel":
                    text.add("یوسرنیم");
                    break;

                case "firstNameLabel":
                    text.add("نام کوچک");
                    break;

                case "lastNameLabel":
                    text.add("نام خانوداگی");
                    break;

                case "addressLabel":
                    text.add("آدرس");
                    break;

                case "nationalIdLabel":
                    text.add("کدملی");
                    break;

                case "emailLabel":
                    text.add("ایمیل");
                    break;

                case "birthDayLabel":
                    text.add("تاریخ تولد");
                    break;

                case "phoneNumberLabel":
                    text.add("شماره تماس");
                    break;

                case "facultyNameLabel":
                    text.add("نام دانشکده");
                    break;

                case "entranceDateLabel":
                    text.add("تاریخ ورود");
                    break;


                case "editPersonalInfoHeader":
                    text.add("تغییر مشخصات فردی");
                    break;


                case "save":
                    text.add("ذخیره");
                    break;

                case "passwordLabel":
                    text.add("رمز جدید");
                    break;

                case "repeatPasswordLabel":
                    text.add("تکرار رمز جدید");
                    break;

                case "universityManagementHeader":
                    text.add("مدیریت\nدانشگاه");
                    break;
            }
        }
    }

    private void englishLanguageTranslator(){

    }

}
