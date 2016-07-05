public interface IPayReceiverListener
{
    //-------------------------------------------------------------------------
    void PayResult(_ePayOptionType option_type, bool is_success, object result);
}
