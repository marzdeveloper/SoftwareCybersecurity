package org.web3j.model;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
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
 * <p>Generated with web3j version 4.2.0.
 */
public class Journal extends Contract {
    private static final String BINARY = "6080604052600060035534801561001557600080fd5b506113e5806100256000396000f3006080604052600436106100775763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663180aedf3811461007c5780632acf2967146100a657806365a990f01461017d57806369d7428f14610257578063827a4c951461030f578063a014ec5214610327575b600080fd5b34801561008857600080fd5b5061009460043561033f565b60408051918252519081900360200190f35b3480156100b257600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261017b94369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506103669650505050505050565b005b34801561018957600080fd5b5060408051602060046024803582810135601f810185900485028601850190965285855261017b95833595369560449491939091019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506109a89650505050505050565b34801561026357600080fd5b5061026f600435610ebe565b604051808481526020018060200180602001838103835285818151815260200191508051906020019060200280838360005b838110156102b95781810151838201526020016102a1565b50505050905001838103825284818151815260200191508051906020019060200280838360005b838110156102f85781810151838201526020016102e0565b505050509050019550505050505060405180910390f35b34801561031b57600080fd5b5061026f600435610fd7565b34801561033357600080fd5b5061026f600435611185565b600480548290811061034d57fe5b6000918252602090912060036004909202010154905081565b61036e611326565b60006002856040516020018082805190602001908083835b602083106103a55780518252601f199092019160209182019101610386565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b602083106104085780518252601f1990920191602091820191016103e9565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610449573d6000803e3d6000fd5b5050506040513d602081101561045e57600080fd5b50518154600181810184556000938452602093849020909101919091556040518551919260029287928201918291908401908083835b602083106104b35780518252601f199092019160209182019101610494565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b602083106105165780518252601f1990920191602091820191016104f7565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610557573d6000803e3d6000fd5b5050506040513d602081101561056c57600080fd5b5051815460018101835560009283526020928390200155604051835160029283928692908201918291908401908083835b602083106105bc5780518252601f19909201916020918201910161059d565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b6020831061061f5780518252601f199092019160209182019101610600565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610660573d6000803e3d6000fd5b5050506040513d602081101561067557600080fd5b50518154600181018355600092835260208084209091019190915560408051835460a0938102820184019092526080810182815290938493919290918401828280156106e157602002820191906000526020600020905b815481526001909101906020018083116106cc575b50505050508152602001600180548060200260200160405190810160405280929190818152602001828054801561073857602002820191906000526020600020905b81548152600190910190602001808311610723575b50505050508152602001600280548060200260200160405190810160405280929190818152602001828054801561078f57602002820191906000526020600020905b8154815260019091019060200180831161077a575b5050509183525050600354602091820152600480546001810180835560008390528451805195965090948694929093027f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b01926107ef928492019061134f565b506020828101518051610808926001850192019061134f565b506040820151805161082491600284019160209091019061134f565b50606082015181600301555050507f97ab1a349b29ea8686e8310cad19d1b089c1df8011dccc8d41d0093d0f9b9008600460035481548110151561086457fe5b9060005260206000209060040201600001600460035481548110151561088657fe5b906000526020600020906004020160010160046003548154811015156108a857fe5b906000526020600020906004020160020160405180806020018060200180602001848103845287818154815260200191508054801561090757602002820191906000526020600020905b815481526001909101906020018083116108f2575b5050848103835286818154815260200191508054801561094757602002820191906000526020600020905b81548152600190910190602001808311610932575b5050848103825285818154815260200191508054801561098757602002820191906000526020600020905b81548152600190910190602001808311610972575b5050965050505050505060405180910390a150506003805460010190555050565b60005b600354811015610eb757846004828154811015156109c557fe5b9060005260206000209060040201600301541415610eaf5760048054829081106109eb57fe5b90600052602060002090600402016001016002856040516020018082805190602001908083835b60208310610a315780518252601f199092019160209182019101610a12565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b60208310610a945780518252601f199092019160209182019101610a75565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610ad5573d6000803e3d6000fd5b5050506040513d6020811015610aea57600080fd5b5051815460018101835560009283526020909220909101556004805482908110610b1057fe5b90600052602060002090600402016002016002846040516020018082805190602001908083835b60208310610b565780518252601f199092019160209182019101610b37565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b60208310610bb95780518252601f199092019160209182019101610b9a565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610bfa573d6000803e3d6000fd5b5050506040513d6020811015610c0f57600080fd5b5051815460018101835560009283526020909220909101556004805482908110610c3557fe5b90600052602060002090600402016000016002836040516020018082805190602001908083835b60208310610c7b5780518252601f199092019160209182019101610c5c565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b60208310610cde5780518252601f199092019160209182019101610cbf565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610d1f573d6000803e3d6000fd5b5050506040513d6020811015610d3457600080fd5b505181546001810183556000928352602090922090910155600480547f97ab1a349b29ea8686e8310cad19d1b089c1df8011dccc8d41d0093d0f9b9008919083908110610d7d57fe5b9060005260206000209060040201600001600483815481101515610d9d57fe5b9060005260206000209060040201600101600484815481101515610dbd57fe5b9060005260206000209060040201600201604051808060200180602001806020018481038452878181548152602001915080548015610e1c57602002820191906000526020600020905b81548152600190910190602001808311610e07575b50508481038352868181548152602001915080548015610e5c57602002820191906000526020600020905b81548152600190910190602001808311610e47575b50508481038252858181548152602001915080548015610e9c57602002820191906000526020600020905b81548152600190910190602001808311610e87575b5050965050505050505060405180910390a15b6001016109ab565b5050505050565b6000606080600484815481101515610ed257fe5b906000526020600020906004020160030154600485815481101515610ef357fe5b9060005260206000209060040201600101600486815481101515610f1357fe5b906000526020600020906004020160020181805480602002602001604051908101604052809291908181526020018280548015610f7057602002820191906000526020600020905b81548152600190910190602001808311610f5b575b5050505050915080805480602002602001604051908101604052809291908181526020018280548015610fc357602002820191906000526020600020905b81548152600190910190602001808311610fae575b505050505090509250925092509193909250565b600060608082805b60045482101561117c575060005b6004805483908110610ffb57fe5b90600052602060002090600402016002018054905081101561117157600480548791908490811061102857fe5b90600052602060002090600402016002018281548110151561104657fe5b600091825260209091200154141561116957600480548390811061106657fe5b90600052602060002090600402016003015460048381548110151561108757fe5b90600052602060002090600402016000016004848154811015156110a757fe5b90600052602060002090600402016001018180548060200260200160405190810160405280929190818152602001828054801561110457602002820191906000526020600020905b815481526001909101906020018083116110ef575b505050505091508080548060200260200160405190810160405280929190818152602001828054801561115757602002820191906000526020600020905b81548152600190910190602001808311611142575b5050505050905094509450945061117c565b600101610fed565b600190910190610fdf565b50509193909250565b600060608082805b60045482101561117c575060005b60048054839081106111a957fe5b90600052602060002090600402016001018054905081101561131b5760048054879190849081106111d657fe5b9060005260206000209060040201600101828154811015156111f457fe5b600091825260209091200154141561131357600480548390811061121457fe5b90600052602060002090600402016003015460048381548110151561123557fe5b906000526020600020906004020160000160048481548110151561125557fe5b9060005260206000209060040201600201818054806020026020016040519081016040528092919081815260200182805480156111045760200282019190600052602060002090815481526001909101906020018083116110ef575050505050915080805480602002602001604051908101604052809291908181526020018280548015611157576020028201919060005260206000209081548152600190910190602001808311611142575050505050905094509450945061117c565b60010161119b565b60019091019061118d565b608060405190810160405280606081526020016060815260200160608152602001600081525090565b82805482825590600052602060002090810192821561138c579160200282015b8281111561138c578251825560209092019160019091019061136f565b5061139892915061139c565b5090565b6113b691905b8082111561139857600081556001016113a2565b905600a165627a7a723058207c500e444cdc52ea7a2bfdc04b0df7d08cefe448362f61e8b7a8eb4402625c780029";

    public static final String FUNC_JOBS = "jobs";

    public static final String FUNC_ADDNEWJOB = "addNewJob";

    public static final String FUNC_UPDATEJOB = "updateJob";

    public static final String FUNC_GETJOBBYID = "getJobById";

    public static final String FUNC_GETJOBBYIMAGE = "getJobByImage";

    public static final String FUNC_GETJOBBYMEASURE = "getJobByMeasure";

    public static final Event EVENTJOB_EVENT = new Event("eventJob", 
            Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}));
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

    public RemoteCall<BigInteger> jobs(BigInteger param0) {
        final Function function = new Function(FUNC_JOBS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addNewJob(String _worker, String _measure, String _image) {
        final Function function = new Function(
                FUNC_ADDNEWJOB, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_worker), 
                new org.web3j.abi.datatypes.Utf8String(_measure), 
                new org.web3j.abi.datatypes.Utf8String(_image)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateJob(BigInteger _jobID, String _measure, String _image, String _worker) {
        final Function function = new Function(
                FUNC_UPDATEJOB, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_jobID), 
                new org.web3j.abi.datatypes.Utf8String(_measure), 
                new org.web3j.abi.datatypes.Utf8String(_image), 
                new org.web3j.abi.datatypes.Utf8String(_worker)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<BigInteger, List<byte[]>, List<byte[]>>> getJobById(BigInteger _id) {
        final Function function = new Function(FUNC_GETJOBBYID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteCall<Tuple3<BigInteger, List<byte[]>, List<byte[]>>>(
                new Callable<Tuple3<BigInteger, List<byte[]>, List<byte[]>>>() {
                    @Override
                    public Tuple3<BigInteger, List<byte[]>, List<byte[]>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, List<byte[]>, List<byte[]>>(
                                (BigInteger) results.get(0).getValue(), 
                                convertToNative((List<Bytes32>) results.get(1).getValue()), 
                                convertToNative((List<Bytes32>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteCall<Tuple3<BigInteger, List<byte[]>, List<byte[]>>> getJobByImage(byte[] _image) {
        final Function function = new Function(FUNC_GETJOBBYIMAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_image)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteCall<Tuple3<BigInteger, List<byte[]>, List<byte[]>>>(
                new Callable<Tuple3<BigInteger, List<byte[]>, List<byte[]>>>() {
                    @Override
                    public Tuple3<BigInteger, List<byte[]>, List<byte[]>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, List<byte[]>, List<byte[]>>(
                                (BigInteger) results.get(0).getValue(), 
                                convertToNative((List<Bytes32>) results.get(1).getValue()), 
                                convertToNative((List<Bytes32>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteCall<Tuple3<BigInteger, List<byte[]>, List<byte[]>>> getJobByMeasure(byte[] _measure) {
        final Function function = new Function(FUNC_GETJOBBYMEASURE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_measure)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteCall<Tuple3<BigInteger, List<byte[]>, List<byte[]>>>(
                new Callable<Tuple3<BigInteger, List<byte[]>, List<byte[]>>>() {
                    @Override
                    public Tuple3<BigInteger, List<byte[]>, List<byte[]>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, List<byte[]>, List<byte[]>>(
                                (BigInteger) results.get(0).getValue(), 
                                convertToNative((List<Bytes32>) results.get(1).getValue()), 
                                convertToNative((List<Bytes32>) results.get(2).getValue()));
                    }
                });
    }

    public List<EventJobEventResponse> getEventJobEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTJOB_EVENT, transactionReceipt);
        ArrayList<EventJobEventResponse> responses = new ArrayList<EventJobEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EventJobEventResponse typedResponse = new EventJobEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.worker = (List<byte[]>) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.measure = (List<byte[]>) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.image = (List<byte[]>) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventJobEventResponse> eventJobEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, EventJobEventResponse>() {
            @Override
            public EventJobEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTJOB_EVENT, log);
                EventJobEventResponse typedResponse = new EventJobEventResponse();
                typedResponse.log = log;
                typedResponse.worker = (List<byte[]>) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.measure = (List<byte[]>) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.image = (List<byte[]>) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<EventJobEventResponse> eventJobEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTJOB_EVENT));
        return eventJobEventFlowable(filter);
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

    @Deprecated
    public static RemoteCall<Journal> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Journal.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Journal> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Journal.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Journal> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Journal.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class EventJobEventResponse {
        public Log log;

        public List<byte[]> worker;

        public List<byte[]> measure;

        public List<byte[]> image;
    }
}
