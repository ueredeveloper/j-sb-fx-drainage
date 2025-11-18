package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ApiResponse<T> {

    private String status;
    private String mensagem;
    private T object;

    public ApiResponse() {
    }

    public ApiResponse(String status, String mensagem, T object) {
        this.status = status;
        this.mensagem = mensagem;
        this.object = object;
    }

    // Getters e Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    // Método auxiliar opcional para sucesso
    public static <T> ApiResponse<T> success(String mensagem, T object) {
        return new ApiResponse<>("sucesso", mensagem, object);
    }

    // Método auxiliar opcional para erro
    public static <T> ApiResponse<T> error(String mensagem, T object) {
        return new ApiResponse<>("erro", mensagem, object);
    }
    
    /**
     * ✅ Método genérico que converte um JSON em ApiResponse<T>
     * Exemplo de uso:
     * ApiResponse<Usuario> r = ApiResponse.fromJson(json, Usuario.class);
     */
    public static <T> ApiResponse<T> fromJson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, TypeToken.getParameterized(ApiResponse.class, clazz).getType());
    }
}