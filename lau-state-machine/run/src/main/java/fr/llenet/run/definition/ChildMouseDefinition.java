package fr.llenet.run.definition;

import fr.llenet.engine.core.State;
import fr.llenet.engine.definition.ProcessDefinition;
import fr.llenet.run.definition.action.*;
import fr.llenet.run.definition.condition.HasCavityCondition;
import fr.llenet.run.definition.condition.MoneyReceivedCondition;
import fr.llenet.run.definition.postaction.UpdateToothOrder;
import fr.llenet.run.model.FallenToothOrder;
import org.springframework.stereotype.Component;

@Component
public class ChildMouseDefinition extends ProcessDefinition {
    @Override
    public FallenToothOrder initProcessedObject() {
        return FallenToothOrder.builder().build();
    }

    @Override
    protected void initProcessingDefinition() {
        State toothReceived = State.withName(StateName.TOOTH_RECEIVED.name());
        State toothVerified = State.withName(StateName.TOOTH_VERIFIED.name());
        State moneyOrdered = State.withName(StateName.MONEY_ORDERED.name());
        State moneyReceived = State.withName(StateName.MONEY_RECEIVED.name());
        State shippingToPillow = State.withName(StateName.SHIPPING_TO_PILLOW.name());
        State complete = State.withName(StateName.COMPLETE.name());

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
                .then(moneyOrdered)
                .withAction(OrderLittleMoney.class)
                .withPauseAfterTransition()
                .withPriority(1);
        toothVerified
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
                .then(moneyOrdered)
                .withPauseAfterTransition();

        moneyReceived
                .then(shippingToPillow)
                .withAction(ShippingToPillowAction.class);

        shippingToPillow
                .then(complete)
                .withAction(CompleteOrderAction.class);
    }

    public enum StateName {
        TOOTH_RECEIVED,
        TOOTH_VERIFIED,
        ORDER_MONEY,
        MONEY_ORDERED,
        MONEY_RECEIVED,
        SHIPPING_TO_PILLOW,
        COMPLETE
    }

}
