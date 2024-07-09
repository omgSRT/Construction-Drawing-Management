package com.GSU24SE43.ConstructionDrawingManagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    INVALID_ERROR_MESSAGE_KEY(10666, "Không tìm thấy tin nhắn lỗi trong danh sách ", HttpStatus.INTERNAL_SERVER_ERROR),
    UNDEFINED_EXCEPTION(10000, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PASSWORD(10001, "Mật khẩu phải có ít nhất 8 kí tự", HttpStatus.BAD_REQUEST),
    INVALID_INPUT(10002, "Thông tin nhập vào không hợp lệ", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(10003, "Mật khẩu mới và xác nhận mật khẩu không trùng khớp", HttpStatus.BAD_REQUEST),
    LOGIN_FAILED(10004, "Email hoặc mật khẩu không hợp lệ", HttpStatus.BAD_REQUEST),
    FIND_FAILED(10005, "Không thể tìm thấy thông tin", HttpStatus.NOT_FOUND),
    EMPTY_LIST(10006, "Danh sách không chứa bất kì thông tin nào", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(10007, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    INVALID_PAGE_NUMBER(10008, "Số trang phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    INVALID_PER_PAGE_NUMBER(10009, "Số data mỗi trang phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN(10010, "Token hết hạn", HttpStatus.REQUEST_TIMEOUT),
    INVALID_TOKEN(10011, "Token không hợp lệ", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(10012, "Không có quyền", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(10013, "Bạn không có quyền cho chức năng này", HttpStatus.UNAUTHORIZED),
    NAME_NOT_BLANK(10014, "Tên không thể để trống", HttpStatus.BAD_REQUEST),
    NAME_EXISTED(10015, "Tên đã tồn tại", HttpStatus.BAD_REQUEST),
    NAME_NOT_FOUND(10016, "Tên không tìm thấy", HttpStatus.NOT_FOUND),
    URL_NOT_BLANK(10017, "URL không thể để trống", HttpStatus.BAD_REQUEST),
    FOLDER_NOT_FOUND(10018, "Không tìm thấy tệp tin", HttpStatus.NOT_FOUND),
    PROJECT_NOT_FOUND(10019, "Không tìm thấy dự án", HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_FOUND(10020, "Không tìm thấy phòng ban", HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_FOUND(10021, "Không tìm thấy tài khoản", HttpStatus.NOT_FOUND),
    INVALID_CREATED_DATE_EARLIER_THAN_END_DATE(10022, "Ngày tạo phải được tạo sớm hơn ngày kết thúc", HttpStatus.BAD_REQUEST),
    INVALID_CREATED_DATE_NOT_IN_FUTURE(10023, "Ngày tạo phải bằng hoặc muộn hơn hiện tại", HttpStatus.BAD_REQUEST),
    INVALID_END_DATE_NOT_IN_FUTURE(10024, "Ngày kết thúc phải muộn hơn hiện tại", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(10025, "Không tìm thấy quyền truy cập", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_EXIST(10026,"Tài khoản không tồn tại",  HttpStatus.NOT_FOUND),
    ACCOUNT_ARE_EXISTED(10027,"Tài khoản đã tồn tại",  HttpStatus.NOT_FOUND),
    STAFF_IS_EXISTED(10028,"Nhân viên đã tồn tại",  HttpStatus.BAD_REQUEST),
    EMAIL_IS_EXISTED(10029,"Email đã được sử dụng",  HttpStatus.BAD_REQUEST),
    ROLE_IS_NOT_DEFINED(10030,"Role không tồn tại",  HttpStatus.BAD_REQUEST),
    UNDEFINED_STATUS_ACCOUNT(10031,"Trạng thái tài khoản không hợp lệ",  HttpStatus.NOT_FOUND),
    USERNAME_TOO_SHORT(10032, "Username phải có ít nhất 8 kí tự", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_SHORT(10033, "Mật khẩu phải có ít nhất 8 kí tự", HttpStatus.BAD_REQUEST),
    INVALID_STATUS(10034, "Data trạng thái không hợp lệ", HttpStatus.BAD_REQUEST),
    USERNAME_IS_EXISTED(10035, "Username đã tồn tại", HttpStatus.BAD_REQUEST),
    CONTENT_NOT_BLANK(10036, "Nội dung không thể để trống", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_FOUND(10037, "Không tìm thấy comment", HttpStatus.NOT_FOUND),
    STAFF_NOT_FOUND(10038, "Không tìm thấy nhân viên", HttpStatus.NOT_FOUND),
    TASK_NOT_FOUND(10039, "Không tìm thấy công việc", HttpStatus.NOT_FOUND),
    UPLOAD_FAILED(10040, "Upload thất bại", HttpStatus.EXPECTATION_FAILED),
    DOWNLOAD_FAILED(10041, "Download thất bại", HttpStatus.EXPECTATION_FAILED),
    INVALID_FILE_NAME(10042, "Tên tệp tin không hợp lệ", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(10043, "Không tìm thấy tài liệu", HttpStatus.NOT_FOUND),
    ACCESS_NOT_FOUND(10044, "Không tìm thấy quyền truy cập", HttpStatus.BAD_REQUEST),
    VERSION_NOT_FOUND(10045, "Không tìm thấy version", HttpStatus.BAD_REQUEST),
    TASK_PARENT_NOT_FOUND(10046, "Không tìm thấy task parent", HttpStatus.BAD_REQUEST),
    TYPE_NOT_BLANK(10047, "Không tìm thấy loại file", HttpStatus.BAD_REQUEST),
    STATUS_NOT_BLANK(10048, "Trạng thái không thể để trống", HttpStatus.BAD_REQUEST),
    DRAWING_NOT_FOUND(10049, "Không tìm thấy bản vẽ", HttpStatus.NOT_FOUND),
    STAFF_OR_ACCOUNT_NOT_FOUND(10050, "Không tìm thấy tài khoản hay nhân viên", HttpStatus.BAD_REQUEST),
    ONLY_ADMIN_CREATE_PROJECT(10051, "Chỉ admin mới có thể tạo dự án", HttpStatus.UNAUTHORIZED),
    EMAIL_SEND_NOT_BLANK(10052, "Emails cần gửi không được trống", HttpStatus.BAD_REQUEST),
    EMAIL_CONTENT_NOT_BLANK(10053, "Chủ đề, nội dung, hoặc tệp đính kèm không được để trống", HttpStatus.BAD_REQUEST),
    INVALID_URL(10054, "URL không hợp lệ", HttpStatus.BAD_REQUEST),
    SEND_MAIL_FAILED(10055, "Gửi mail thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    ILLEGAL_STATE_EXCEPTION(10056, "Không thể gọi hoặc hỗ trợ phương thức này", HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_BEGINDATE_OR_ENDDATE(10057,"Ngày tạo phải được tạo sớm hơn ngày kết thúc",HttpStatus.BAD_REQUEST),
    PRIORITY_INVALID(10058,"Độ ưu tiên không hợp lệ",HttpStatus.BAD_REQUEST),
    PRIORITY_IS_DUPLICATE(10059,"Độ ưu tiên trùng lặp",HttpStatus.BAD_REQUEST),
    TITLE_NOT_BLANK(10060, "Tiêu đề không được để tróng", HttpStatus.BAD_REQUEST),
    MESSAGE_NOT_BLANK(10061, "Tin nhắn không được để tróng", HttpStatus.BAD_REQUEST),
    NOTIFICATION_NOT_FOUND(10062, "Không tìm thấy thông báo", HttpStatus.NOT_FOUND),
    DUPLICATE_HEAD(10063, "Trưởng phòng ban trùng lặp", HttpStatus.NOT_FOUND),
    DETAIL_TASK_NOT_FOUND(10064, "Không tìm thấy chi tiết công việc", HttpStatus.NOT_FOUND),
    STAFF_FOLDER_NOT_FOUND(10065, "Không tìm thấy staff folder", HttpStatus.NOT_FOUND),
    TASK_CHILD_NOT_FOUND(10066, "Không tìm thấy task con", HttpStatus.NOT_FOUND),
    UNDEFINED_STATUS_TASK(10067, "Trạng thái công việc không hợp lệ", HttpStatus.BAD_REQUEST),
    LOG_NOT_FOUND(10068, "Không tìm thấy nhật kí", HttpStatus.NOT_FOUND),
    ROOM_HAD_HEAD(10069, "Phòng này đã có trưởng phòng", HttpStatus.BAD_REQUEST),

    ;



    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode){
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
