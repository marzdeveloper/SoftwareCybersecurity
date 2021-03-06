package com.sc.webim.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
 * <p>Generated with web3j version 4.6.1.
 */
@SuppressWarnings("rawtypes")
public class Journal extends Contract {
    public static final String BINARY = "6080604052600060015534801561001557600080fd5b50600080546001600160a01b031916331790556107c5806100376000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80634640967a1461003b57806369d7428f1461016a575b600080fd5b6101686004803603604081101561005157600080fd5b81019060208101813564010000000081111561006c57600080fd5b82018360208201111561007e57600080fd5b803590602001918460018302840111640100000000831117156100a057600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092959493602081019350359150506401000000008111156100f357600080fd5b82018360208201111561010557600080fd5b8035906020019184600183028401116401000000008311171561012757600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061026c945050505050565b005b6101876004803603602081101561018057600080fd5b5035610542565b604051808481526020018060200180602001838103835285818151815260200191508051906020019080838360005b838110156101ce5781810151838201526020016101b6565b50505050905090810190601f1680156101fb5780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b8381101561022e578181015183820152602001610216565b50505050905090810190601f16801561025b5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b6000546001600160a01b0316331461028357600080fd5b61028b6106cb565b5060408051608081018252600080546001600160a01b039081168352602080840187815294840186905260018054606086015260028054918201815590935283517f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace600490940293840180546001600160a01b0319169190931617825593518051939485949293610344937f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5acf90910192909101906106fc565b50604082015180516103609160028401916020909101906106fc565b506060820151816003015550507f2234ca8786b5713784ae6452c76a99a2283a03a9625f8bc04b2ddd682a4f221060026001548154811061039d57fe5b906000526020600020906004020160000160009054906101000a90046001600160a01b03166002600154815481106103d157fe5b90600052602060002090600402016001016002600154815481106103f157fe5b90600052602060002090600402016002016001544260405180866001600160a01b0316815260200180602001806020018581526020018481526020018381038352878181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156104ae5780601f10610483576101008083540402835291602001916104ae565b820191906000526020600020905b81548152906001019060200180831161049157829003601f168201915b50508381038252865460026000196101006001841615020190911604808252602090910190879080156105225780601f106104f757610100808354040283529160200191610522565b820191906000526020600020905b81548152906001019060200180831161050557829003601f168201915b505097505050505050505060405180910390a15050600180548101905550565b60006060806002848154811061055457fe5b9060005260206000209060040201600301546002858154811061057357fe5b90600052602060002090600402016001016002868154811061059157fe5b6000918252602091829020835460408051601f600260001961010060018716150201909416849004908101879004870282018701909252818152600490940290920101928491908301828280156106295780601f106105fe57610100808354040283529160200191610629565b820191906000526020600020905b81548152906001019060200180831161060c57829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959750869450925084019050828280156106b75780601f1061068c576101008083540402835291602001916106b7565b820191906000526020600020905b81548152906001019060200180831161069a57829003601f168201915b505050505090509250925092509193909250565b604051806080016040528060006001600160a01b031681526020016060815260200160608152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061073d57805160ff191683800117855561076a565b8280016001018555821561076a579182015b8281111561076a57825182559160200191906001019061074f565b5061077692915061077a565b5090565b5b80821115610776576000815560010161077b56fea26469706673582212200896a35bde9edbd902f14d835c1558582211eb3c323f845ad4397d59155bd5c364736f6c634300060c0033";

    public static final String FUNC_ADDNEWJOB = "addNewJob";

    public static final String FUNC_GETJOBBYID = "getJobById";

    public static final Event EVENTJOB_EVENT = new Event("eventJob", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Journal(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Journal(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Journal(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Journal(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<EventJobEventResponse> getEventJobEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTJOB_EVENT, transactionReceipt);
        ArrayList<EventJobEventResponse> responses = new ArrayList<EventJobEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EventJobEventResponse typedResponse = new EventJobEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.worker = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.measure = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.images = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.jobId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventJobEventResponse> eventJobEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, EventJobEventResponse>() {
            @Override
            public EventJobEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTJOB_EVENT, log);
                EventJobEventResponse typedResponse = new EventJobEventResponse();
                typedResponse.log = log;
                typedResponse.worker = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.measure = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.images = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.jobId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<EventJobEventResponse> eventJobEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTJOB_EVENT));
        return eventJobEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addNewJob(String measure, String images) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDNEWJOB, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(measure), 
                new org.web3j.abi.datatypes.Utf8String(images)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, String, String>> getJobById(BigInteger _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETJOBBYID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, String, String>>(function,
                new Callable<Tuple3<BigInteger, String, String>>() {
                    @Override
                    public Tuple3<BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    @Deprecated
    public static Journal load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Journal(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Journal load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Journal(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Journal load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Journal(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Journal load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Journal(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Journal> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Journal.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Journal> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Journal.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Journal> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Journal.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Journal> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Journal.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class EventJobEventResponse extends BaseEventResponse {
        public String worker;

        public String measure;

        public String images;

        public BigInteger jobId;

        public BigInteger time;
    }
}