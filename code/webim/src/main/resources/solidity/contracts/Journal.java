package org.web3j.model;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
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
    private static final String BINARY = "6080604052600060035534801561001557600080fd5b5061111b806100256000396000f3006080604052600436106100775763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663180aedf3811461007c5780632acf2967146100a657806365a990f01461017d57806369d7428f14610257578063827a4c951461030f578063a014ec5214610327575b600080fd5b34801561008857600080fd5b5061009460043561033f565b60408051918252519081900360200190f35b3480156100b257600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261017b94369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506103669650505050505050565b005b34801561018957600080fd5b5060408051602060046024803582810135601f810185900485028601850190965285855261017b95833595369560449491939091019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506108409650505050505050565b34801561026357600080fd5b5061026f600435610bf4565b604051808481526020018060200180602001838103835285818151815260200191508051906020019060200280838360005b838110156102b95781810151838201526020016102a1565b50505050905001838103825284818151815260200191508051906020019060200280838360005b838110156102f85781810151838201526020016102e0565b505050509050019550505050505060405180910390f35b34801561031b57600080fd5b5061026f600435610d0d565b34801561033357600080fd5b5061026f600435610ebb565b600480548290811061034d57fe5b6000918252602090912060036004909202010154905081565b61036e61105c565b60006002856040516020018082805190602001908083835b602083106103a55780518252601f199092019160209182019101610386565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b602083106104085780518252601f1990920191602091820191016103e9565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610449573d6000803e3d6000fd5b5050506040513d602081101561045e57600080fd5b50518154600181810184556000938452602093849020909101919091556040518551919260029287928201918291908401908083835b602083106104b35780518252601f199092019160209182019101610494565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b602083106105165780518252601f1990920191602091820191016104f7565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610557573d6000803e3d6000fd5b5050506040513d602081101561056c57600080fd5b5051815460018101835560009283526020928390200155604051835160029283928692908201918291908401908083835b602083106105bc5780518252601f19909201916020918201910161059d565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b6020831061061f5780518252601f199092019160209182019101610600565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610660573d6000803e3d6000fd5b5050506040513d602081101561067557600080fd5b50518154600181018355600092835260208084209091019190915560408051835460a0938102820184019092526080810182815290938493919290918401828280156106e157602002820191906000526020600020905b815481526001909101906020018083116106cc575b50505050508152602001600180548060200260200160405190810160405280929190818152602001828054801561073857602002820191906000526020600020905b81548152600190910190602001808311610723575b50505050508152602001600280548060200260200160405190810160405280929190818152602001828054801561078f57602002820191906000526020600020905b8154815260019091019060200180831161077a575b5050509183525050600354602091820152600480546001810180835560008390528451805195965090948694929093027f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b01926107ef9284920190611085565b5060208281015180516108089260018501920190611085565b5060408201518051610824916002840191602090910190611085565b5060609190910151600391820155805460010190555050505050565b60005b600354811015610bed578460048281548110151561085d57fe5b9060005260206000209060040201600301541415610be557600480548290811061088357fe5b90600052602060002090600402016001016002856040516020018082805190602001908083835b602083106108c95780518252601f1990920191602091820191016108aa565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b6020831061092c5780518252601f19909201916020918201910161090d565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af115801561096d573d6000803e3d6000fd5b5050506040513d602081101561098257600080fd5b50518154600181018355600092835260209092209091015560048054829081106109a857fe5b90600052602060002090600402016002016002846040516020018082805190602001908083835b602083106109ee5780518252601f1990920191602091820191016109cf565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b60208310610a515780518252601f199092019160209182019101610a32565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610a92573d6000803e3d6000fd5b5050506040513d6020811015610aa757600080fd5b5051815460018101835560009283526020909220909101556004805482908110610acd57fe5b90600052602060002090600402016000016002836040516020018082805190602001908083835b60208310610b135780518252601f199092019160209182019101610af4565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b60208310610b765780518252601f199092019160209182019101610b57565b51815160209384036101000a600019018019909216911617905260405191909301945091925050808303816000865af1158015610bb7573d6000803e3d6000fd5b5050506040513d6020811015610bcc57600080fd5b5051815460018101835560009283526020909220909101555b600101610843565b5050505050565b6000606080600484815481101515610c0857fe5b906000526020600020906004020160030154600485815481101515610c2957fe5b9060005260206000209060040201600101600486815481101515610c4957fe5b906000526020600020906004020160020181805480602002602001604051908101604052809291908181526020018280548015610ca657602002820191906000526020600020905b81548152600190910190602001808311610c91575b5050505050915080805480602002602001604051908101604052809291908181526020018280548015610cf957602002820191906000526020600020905b81548152600190910190602001808311610ce4575b505050505090509250925092509193909250565b600060608082805b600454821015610eb2575060005b6004805483908110610d3157fe5b906000526020600020906004020160020180549050811015610ea7576004805487919084908110610d5e57fe5b906000526020600020906004020160020182815481101515610d7c57fe5b6000918252602090912001541415610e9f576004805483908110610d9c57fe5b906000526020600020906004020160030154600483815481101515610dbd57fe5b9060005260206000209060040201600001600484815481101515610ddd57fe5b906000526020600020906004020160010181805480602002602001604051908101604052809291908181526020018280548015610e3a57602002820191906000526020600020905b81548152600190910190602001808311610e25575b5050505050915080805480602002602001604051908101604052809291908181526020018280548015610e8d57602002820191906000526020600020905b81548152600190910190602001808311610e78575b50505050509050945094509450610eb2565b600101610d23565b600190910190610d15565b50509193909250565b600060608082805b600454821015610eb2575060005b6004805483908110610edf57fe5b906000526020600020906004020160010180549050811015611051576004805487919084908110610f0c57fe5b906000526020600020906004020160010182815481101515610f2a57fe5b6000918252602090912001541415611049576004805483908110610f4a57fe5b906000526020600020906004020160030154600483815481101515610f6b57fe5b9060005260206000209060040201600001600484815481101515610f8b57fe5b906000526020600020906004020160020181805480602002602001604051908101604052809291908181526020018280548015610e3a576020028201919060005260206000209081548152600190910190602001808311610e25575050505050915080805480602002602001604051908101604052809291908181526020018280548015610e8d576020028201919060005260206000209081548152600190910190602001808311610e785750505050509050945094509450610eb2565b600101610ed1565b600190910190610ec3565b608060405190810160405280606081526020016060815260200160608152602001600081525090565b8280548282559060005260206000209081019282156110c2579160200282015b828111156110c257825182556020909201916001909101906110a5565b506110ce9291506110d2565b5090565b6110ec91905b808211156110ce57600081556001016110d8565b905600a165627a7a723058206c0dcfe7c5c4ca29dd80e43baac5b608db9840219f1bdc5d0117f268a376e7af0029";

    public static final String FUNC_JOBS = "jobs";

    public static final String FUNC_ADDNEWJOB = "addNewJob";

    public static final String FUNC_UPDATEJOB = "updateJob";

    public static final String FUNC_GETJOBBYID = "getJobById";

    public static final String FUNC_GETJOBBYIMAGE = "getJobByImage";

    public static final String FUNC_GETJOBBYMEASURE = "getJobByMeasure";

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
}