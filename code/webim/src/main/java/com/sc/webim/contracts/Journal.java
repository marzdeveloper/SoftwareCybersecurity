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
import org.web3j.tuples.generated.Tuple4;
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
    public static final String BINARY = "6080604052600060015534801561001557600080fd5b50600080546001600160a01b03191633179055610a31806100376000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c8063180aedf3146100465780634640967a1461015857806369d7428f14610287575b600080fd5b6100636004803603602081101561005c57600080fd5b5035610389565b60405180856001600160a01b031681526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b838110156100b95781810151838201526020016100a1565b50505050905090810190601f1680156100e65780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b83811015610119578181015183820152602001610101565b50505050905090810190601f1680156101465780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b6102856004803603604081101561016e57600080fd5b81019060208101813564010000000081111561018957600080fd5b82018360208201111561019b57600080fd5b803590602001918460018302840111640100000000831117156101bd57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561021057600080fd5b82018360208201111561022257600080fd5b8035906020019184600183028401116401000000008311171561024457600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506104e5945050505050565b005b6102a46004803603602081101561029d57600080fd5b50356107ae565b604051808481526020018060200180602001838103835285818151815260200191508051906020019080838360005b838110156102eb5781810151838201526020016102d3565b50505050905090810190601f1680156103185780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b8381101561034b578181015183820152602001610333565b50505050905090810190601f1680156103785780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b6002818154811061039657fe5b600091825260209182902060049091020180546001808301805460408051601f60026000199685161561010002969096019093169490940491820187900487028401870190528083526001600160a01b0390931695509293909291908301828280156104435780601f1061041857610100808354040283529160200191610443565b820191906000526020600020905b81548152906001019060200180831161042657829003601f168201915b50505060028085018054604080516020601f60001961010060018716150201909416959095049283018590048502810185019091528181529596959450909250908301828280156104d55780601f106104aa576101008083540402835291602001916104d5565b820191906000526020600020905b8154815290600101906020018083116104b857829003601f168201915b5050505050908060030154905084565b600080546001600160a01b031916331790556104ff610937565b5060408051608081018252600080546001600160a01b039081168352602080840187815294840186905260018054606086015260028054918201815590935283517f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace600490940293840180546001600160a01b03191691909316178255935180519394859492936105b8937f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5acf9091019290910190610968565b50604082015180516105d4916002840191602090910190610968565b506060820151816003015550507f79bd7258b325ded0b372826bb7bf1a4bfb13b2fa410e5650339233617546c42d60026001548154811061061157fe5b906000526020600020906004020160000160009054906101000a90046001600160a01b031660026001548154811061064557fe5b906000526020600020906004020160010160026001548154811061066557fe5b906000526020600020906004020160020160015460405180856001600160a01b03168152602001806020018060200184815260200183810383528681815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561071b5780601f106106f05761010080835404028352916020019161071b565b820191906000526020600020905b8154815290600101906020018083116106fe57829003601f168201915b505083810382528554600260001961010060018416150201909116048082526020909101908690801561078f5780601f106107645761010080835404028352916020019161078f565b820191906000526020600020905b81548152906001019060200180831161077257829003601f168201915b5050965050505050505060405180910390a15050600180548101905550565b6000606080600284815481106107c057fe5b906000526020600020906004020160030154600285815481106107df57fe5b9060005260206000209060040201600101600286815481106107fd57fe5b6000918252602091829020835460408051601f600260001961010060018716150201909416849004908101879004870282018701909252818152600490940290920101928491908301828280156108955780601f1061086a57610100808354040283529160200191610895565b820191906000526020600020905b81548152906001019060200180831161087857829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959750869450925084019050828280156109235780601f106108f857610100808354040283529160200191610923565b820191906000526020600020905b81548152906001019060200180831161090657829003601f168201915b505050505090509250925092509193909250565b604051806080016040528060006001600160a01b031681526020016060815260200160608152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106109a957805160ff19168380011785556109d6565b828001600101855582156109d6579182015b828111156109d65782518255916020019190600101906109bb565b506109e29291506109e6565b5090565b5b808211156109e257600081556001016109e756fea2646970667358221220b986bf0a423db7061c76c9ef778e0a0df35301b2a4dd79cf004a58300975723464736f6c634300060c0033";

    public static final String FUNC_ADDNEWJOB = "addNewJob";

    public static final String FUNC_GETJOBBYID = "getJobById";

    public static final String FUNC_JOBS = "jobs";

    public static final Event EVENTJOB_EVENT = new Event("eventJob", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
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

    public RemoteFunctionCall<Tuple4<String, String, String, BigInteger>> jobs(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_JOBS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<String, String, String, BigInteger>>(function,
                new Callable<Tuple4<String, String, String, BigInteger>>() {
                    @Override
                    public Tuple4<String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
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
    }
}