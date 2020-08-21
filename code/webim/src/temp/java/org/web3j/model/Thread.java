package org.web3j.model;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class Thread extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610717806100206000396000f30060806040526004361061006c5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630d80fefd81146100715780636c4470fb146100fe578063a743427f14610113578063d62116df146101ac578063f6b4dfb41461021d575b600080fd5b34801561007d57600080fd5b5061008960043561025b565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100c35781810151838201526020016100ab565b50505050905090810190601f1680156100f05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561010a57600080fd5b50610089610302565b34801561011f57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101aa94369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375094975061035d9650505050505050565b005b3480156101b857600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101aa9436949293602493928401919081908401838280828437509497505050923573ffffffffffffffffffffffffffffffffffffffff16935061052592505050565b34801561022957600080fd5b50610232610634565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b600280548290811061026957fe5b600091825260209182902001805460408051601f60026000196101006001871615020190941693909304928301859004850281018501909152818152935090918301828280156102fa5780601f106102cf576101008083540402835291602001916102fa565b820191906000526020600020905b8154815290600101906020018083116102dd57829003601f168201915b505050505081565b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102fa5780601f106102cf576101008083540402835291602001916102fa565b600280546001810180835560009290925283516103a1917f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace01906020860190610650565b505060408051606080825260008054600260001961010060018416150201909116049183018290527f0eabeffe119b8ffbb23292e86677821e520cbaeb5401f69cb0d565b69fae8e6f939092869286928291602083019183019060808401908890801561044f5780601f106104245761010080835404028352916020019161044f565b820191906000526020600020905b81548152906001019060200180831161043257829003601f168201915b5050848103835286518152865160209182019188019080838360005b8381101561048357818101518382015260200161046b565b50505050905090810190601f1680156104b05780820380516001836020036101000a031916815260200191505b50848103825285518152855160209182019187019080838360005b838110156104e35781810151838201526020016104cb565b50505050905090810190601f1680156105105780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050565b8151610538906000906020850190610650565b506001805473ffffffffffffffffffffffffffffffffffffffff191673ffffffffffffffffffffffffffffffffffffffff8381169190911780835560408051919092166020820181905282825260008054600261010096821615969096026000190116949094049282018390527f4cf8037dff8f2e4212332ce6a37f5353c431bfc409fe36d824e7553dbaf66b86939290919081906060820190859080156106215780601f106105f657610100808354040283529160200191610621565b820191906000526020600020905b81548152906001019060200180831161060457829003601f168201915b5050935050505060405180910390a15050565b60015473ffffffffffffffffffffffffffffffffffffffff1681565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061069157805160ff19168380011785556106be565b828001600101855582156106be579182015b828111156106be5782518255916020019190600101906106a3565b506106ca9291506106ce565b5090565b6106e891905b808211156106ca57600081556001016106d4565b905600a165627a7a723058205b805e8d77baec065e85ce18b12cdd2c3d747725afc42a1c4a2d9a31d5ce12be0029";

    public static final String FUNC_MESSAGES = "messages";

    public static final String FUNC_PARTICIPANTS = "participants";

    public static final String FUNC_SENDMESSAGETOTHREAD = "sendMessageToThread";

    public static final String FUNC_STARTTHREAD = "startThread";

    public static final String FUNC_CONTRACTADDRESS = "contractAddress";

    public static final Event SENDMESSAGE_EVENT = new Event("sendMessage", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event SENDCONTRACTADDRESS_EVENT = new Event("sendContractAddress", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected Thread(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Thread(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Thread(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Thread(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> messages(BigInteger param0) {
        final Function function = new Function(FUNC_MESSAGES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> participants() {
        final Function function = new Function(FUNC_PARTICIPANTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> sendMessageToThread(String _reply, String _messageSender) {
        final Function function = new Function(
                FUNC_SENDMESSAGETOTHREAD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_reply), 
                new org.web3j.abi.datatypes.Utf8String(_messageSender)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> startThread(String _participants, String _contractAddress) {
        final Function function = new Function(
                FUNC_STARTTHREAD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_participants), 
                new org.web3j.abi.datatypes.Address(_contractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> contractAddress() {
        final Function function = new Function(FUNC_CONTRACTADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<SendMessageEventResponse> getSendMessageEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SENDMESSAGE_EVENT, transactionReceipt);
        ArrayList<SendMessageEventResponse> responses = new ArrayList<SendMessageEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SendMessageEventResponse typedResponse = new SendMessageEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.participants = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.message = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.messageSender = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SendMessageEventResponse> sendMessageEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, SendMessageEventResponse>() {
            @Override
            public SendMessageEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SENDMESSAGE_EVENT, log);
                SendMessageEventResponse typedResponse = new SendMessageEventResponse();
                typedResponse.log = log;
                typedResponse.participants = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.message = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.messageSender = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SendMessageEventResponse> sendMessageEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SENDMESSAGE_EVENT));
        return sendMessageEventFlowable(filter);
    }

    public List<SendContractAddressEventResponse> getSendContractAddressEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SENDCONTRACTADDRESS_EVENT, transactionReceipt);
        ArrayList<SendContractAddressEventResponse> responses = new ArrayList<SendContractAddressEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SendContractAddressEventResponse typedResponse = new SendContractAddressEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.participants = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.contractAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SendContractAddressEventResponse> sendContractAddressEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, SendContractAddressEventResponse>() {
            @Override
            public SendContractAddressEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SENDCONTRACTADDRESS_EVENT, log);
                SendContractAddressEventResponse typedResponse = new SendContractAddressEventResponse();
                typedResponse.log = log;
                typedResponse.participants = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.contractAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SendContractAddressEventResponse> sendContractAddressEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SENDCONTRACTADDRESS_EVENT));
        return sendContractAddressEventFlowable(filter);
    }

    @Deprecated
    public static Thread load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Thread(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Thread load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Thread(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Thread load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Thread(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Thread load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Thread(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Thread> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Thread.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Thread> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Thread.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Thread> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Thread.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Thread> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Thread.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class SendMessageEventResponse {
        public Log log;

        public String participants;

        public String message;

        public String messageSender;
    }

    public static class SendContractAddressEventResponse {
        public Log log;

        public String participants;

        public String contractAddress;
    }
}
