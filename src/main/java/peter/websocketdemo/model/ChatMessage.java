package peter.websocketdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String created;
}
