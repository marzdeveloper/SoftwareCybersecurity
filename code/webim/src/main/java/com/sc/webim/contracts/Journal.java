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
import org.web3j.abi.datatypes.DynamicArray;
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
    public static final String BINARY = "6080604052600060015534801561001557600080fd5b50600080546001600160a01b03191633179055610a3c806100376000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806302ef646114610046578063180aedf31461005b57806369d7428f14610086575b600080fd5b6100596100543660046106e2565b6100a8565b005b61006e610069366004610797565b610261565b60405161007d9392919061087d565b60405180910390f35b610099610094366004610797565b61032b565b60405161007d9392919061093e565b600080546001600160a01b031916331790556100c26104f8565b5060408051608081018252600080546001600160a01b039081168352602080840187815294840186905260018054606086015260028054918201815590935283517f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace600490940293840180546001600160a01b031916919093161782559351805193948594929361017b937f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5acf9091019290910190610529565b50604082015180516101979160028401916020909101906105a7565b506060820151816003015550507fe789b4eeafbb3620234d3bd2c4767fd4e3baf0f8291ee7cb387280ce129e41d16002600154815481106101d457fe5b906000526020600020906004020160000160009054906101000a90046001600160a01b031660026001548154811061020857fe5b906000526020600020906004020160010160026001548154811061022857fe5b906000526020600020906004020160020160015460405161024c94939291906108b1565b60405180910390a15050600180548101905550565b6002818154811061026e57fe5b600091825260209182902060049091020180546001808301805460408051601f60026000199685161561010002969096019093169490940491820187900487028401870190528083526001600160a01b03909316955092939092919083018282801561031b5780601f106102f05761010080835404028352916020019161031b565b820191906000526020600020905b8154815290600101906020018083116102fe57829003601f168201915b5050505050908060030154905083565b60006060806002848154811061033d57fe5b9060005260206000209060040201600301546002858154811061035c57fe5b90600052602060002090600402016001016002868154811061037a57fe5b6000918252602091829020835460408051601f600260001961010060018716150201909416849004908101879004870282018701909252818152600490940290920101928491908301828280156104125780601f106103e757610100808354040283529160200191610412565b820191906000526020600020905b8154815290600101906020018083116103f557829003601f168201915b5050505050915080805480602002602001604051908101604052809291908181526020016000905b828210156104e55760008481526020908190208301805460408051601f60026000196101006001871615020190941693909304928301859004850281018501909152818152928301828280156104d15780601f106104a6576101008083540402835291602001916104d1565b820191906000526020600020905b8154815290600101906020018083116104b457829003601f168201915b50505050508152602001906001019061043a565b5050505090509250925092509193909250565b604051806080016040528060006001600160a01b031681526020016060815260200160608152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061056a57805160ff1916838001178555610597565b82800160010185558215610597579182015b8281111561059757825182559160200191906001019061057c565b506105a3929150610600565b5090565b8280548282559060005260206000209081019282156105f4579160200282015b828111156105f457825180516105e4918491602090910190610529565b50916020019190600101906105c7565b506105a3929150610615565b5b808211156105a35760008155600101610601565b808211156105a35760006106298282610632565b50600101610615565b50805460018160011615610100020316600290046000825580601f106106585750610676565b601f0160209004906000526020600020908101906106769190610600565b50565b600082601f830112610689578081fd5b813567ffffffffffffffff81111561069f578182fd5b6106b2601f8201601f19166020016109b3565b91508082528360208285010111156106c957600080fd5b8060208401602084013760009082016020015292915050565b600080604083850312156106f4578182fd5b823567ffffffffffffffff8082111561070b578384fd5b61071786838701610679565b935060209150818501358181111561072d578384fd5b85019050601f8101861361073f578283fd5b803561075261074d826109da565b6109b3565b81815283810190838501865b84811015610787576107758b888435890101610679565b8452928601929086019060010161075e565b5096999098509650505050505050565b6000602082840312156107a8578081fd5b5035919050565b60008151808452815b818110156107d4576020818501810151868301820152016107b8565b818111156107e55782602083870101525b50601f01601f19169290920160200192915050565b60008154600180821660008114610818576001811461083657610874565b60028304607f16865260ff1983166020870152604086019350610874565b60028304808752610846866109fa565b60005b8281101561086a5781546020828b0101528482019150602081019050610849565b8801602001955050505b50505092915050565b6001600160a01b03841681526060602082018190526000906108a1908301856107af565b9050826040830152949350505050565b6001600160a01b0385168152608060208083018290526000916108d6908401876107fa565b8381036040850152808654808352838301915083848202840101888652848620865b8381101561092557858303601f1901855261091383836107fa565b948701949250600191820191016108f8565b5050809550505050505082606083015295945050505050565b6000848252602060608184015261095860608401866107af565b838103604085015284518082528282019083810283018401848801865b838110156109a357601f198684030185526109918383516107af565b94870194925090860190600101610975565b50909a9950505050505050505050565b60405181810167ffffffffffffffff811182821017156109d257600080fd5b604052919050565b600067ffffffffffffffff8211156109f0578081fd5b5060209081020190565b6000908152602090209056fea2646970667358221220aa7d3bf3d7348b98ede1494b0496805ce07858c830481a140c7686454518e76964736f6c634300060c0033";

    public static final String FUNC_ADDNEWJOB = "addNewJob";

    public static final String FUNC_GETJOBBYID = "getJobById";

    public static final String FUNC_JOBS = "jobs";

    public static final Event EVENTJOB_EVENT = new Event("eventJob", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<DynamicArray<Utf8String>>() {}, new TypeReference<Uint256>() {}));
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
            typedResponse.images = (List<String>) eventValues.getNonIndexedValues().get(2).getValue();
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
                typedResponse.images = (List<String>) eventValues.getNonIndexedValues().get(2).getValue();
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

    public RemoteFunctionCall<TransactionReceipt> addNewJob(String measure, List<String> images) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDNEWJOB, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(measure), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Utf8String>(
                        org.web3j.abi.datatypes.Utf8String.class,
                        org.web3j.abi.Utils.typeMap(images, org.web3j.abi.datatypes.Utf8String.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, String, List<String>>> getJobById(BigInteger _id) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETJOBBYID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<DynamicArray<Utf8String>>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, String, List<String>>>(function,
                new Callable<Tuple3<BigInteger, String, List<String>>>() {
                    @Override
                    public Tuple3<BigInteger, String, List<String>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, String, List<String>>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                convertToNative((List<Utf8String>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<Tuple3<String, String, BigInteger>> jobs(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_JOBS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, BigInteger>>(function,
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
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

        public List<String> images;

        public BigInteger jobId;
    }
}
