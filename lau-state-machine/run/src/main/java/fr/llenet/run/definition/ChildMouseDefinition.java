package fr.llenet.run.definition;

import fr.llenet.engine.core.State;
import fr.llenet.engine.definition.ProcessDefinition;
import fr.llenet.run.definition.ChildMouseDefinition.StateName.Fields;
import fr.llenet.run.definition.action.*;
import fr.llenet.run.definition.condition.HasCavityCondition;
import fr.llenet.run.definition.condition.MoneyReceivedCondition;
import fr.llenet.run.definition.condition.RelaunchOrderMoneyCondition;
import fr.llenet.run.definition.postaction.UpdateToothOrder;
import fr.llenet.run.model.FallenToothOrder;
import lombok.experimental.FieldNameConstants;
import org.springframework.stereotype.Component;

@Component
public class ChildMouseDefinition extends ProcessDefinition {
    @Override
    public FallenToothOrder initProcessedObject() {
        return FallenToothOrder.builder().build();
    }

    @Override
    protected void initProcessingDefinition() {
        State toothReceived = State.withName(Fields.TOOTH_RECEIVED);
        State toothVerified = State.withName(Fields.TOOTH_VERIFIED);
        State orderMoney = State.withName(Fields.ORDER_MONEY);
        State moneyOrdered = State.withName(Fields.MONEY_ORDERED);
        State moneyReceived = State.withName(Fields.MONEY_RECEIVED);
        State shippingToPillow = State.withName(Fields.SHIPPING_TO_PILLOW);
        State complete = State.withName(Fields.COMPLETE);

        startState
                .then(toothReceived)
                .withAction(ToothReceivedAction.class)
                .withPostActions(UpdateToothOrder.class);

        toothReceived
                .then(toothVerified)
                .withAction(VerifyToothAction.class)
                .withPostActions(UpdateToothOrder.class);

        toothVerified
                .when(HasCavityCondition.class)
                .then(orderMoney)
                .withAction(SendToothToTrash.class)
                .withPriority(1);
        toothVerified
                .then(orderMoney)
                .withAction(SendToothToCastle.class);

        orderMoney
                .when(HasCavityCondition.class)
                .then(moneyOrdered)
                .withAction(OrderLittleMoney.class)
                .withPauseAfterTransition()
                .withPriority(1);
        orderMoney
                .then(moneyOrdered)
                .withAction(OrderBigMoney.class)
                .withPauseAfterTransition();


        // relaunch with controller
        moneyOrdered
                .when(MoneyReceivedCondition.class)
                .then(moneyReceived)
                .withAction(MoneyReceivedAction.class)
                .withPostActions(UpdateToothOrder.class)
                .withPriority(2);
        moneyOrdered
                .when(RelaunchOrderMoneyCondition.class)
                .then(orderMoney)
                .withPriority(1);
        moneyOrdered
                .then(moneyOrdered)
                .withPauseAfterTransition();

        moneyReceived
                .then(shippingToPillow)
                .withAction(ShippingToPillowAction.class);

        shippingToPillow
                .then(complete)
                .withAction(CompleteOrderAction.class);
    }

    @FieldNameConstants
    public static class StateName {
        private String toothReceived;
        private String toothVerified;
        private String orderMoney;
        private String moneyOrdered;
        private String moneyReceived;
        private String shippingToPillow;
        private String complete;
    }

}
